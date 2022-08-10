package al.ikubinfo.hrmanagement.services;

import al.ikubinfo.hrmanagement.converters.UserConverter;
import al.ikubinfo.hrmanagement.dto.EmailMessage;
import al.ikubinfo.hrmanagement.dto.requestdtos.NewRequestDto;
import al.ikubinfo.hrmanagement.dto.requestdtos.RequestDto;
import al.ikubinfo.hrmanagement.dto.requestdtos.StatusEnum;
import al.ikubinfo.hrmanagement.dto.roledtos.RoleDto;
import al.ikubinfo.hrmanagement.dto.userdtos.MinimalUserDto;
import al.ikubinfo.hrmanagement.dto.userdtos.UserDto;
import al.ikubinfo.hrmanagement.exception.*;
import al.ikubinfo.hrmanagement.converters.RequestConverter;
import al.ikubinfo.hrmanagement.entity.HolidayEntity;
import al.ikubinfo.hrmanagement.entity.RequestEntity;
import al.ikubinfo.hrmanagement.entity.UserEntity;
import al.ikubinfo.hrmanagement.repository.HolidayRepository;
import al.ikubinfo.hrmanagement.repository.RequestRepository;
import al.ikubinfo.hrmanagement.repository.UserRepository;
import al.ikubinfo.hrmanagement.security.RoleEnum;
import al.ikubinfo.hrmanagement.security.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestConverter requestConverter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HolidayRepository holidayRepository;

     @Autowired
     private EmailService emailService;

    private List<LocalDate> holidays;

    public RequestDto getRequestDto(RequestEntity request) {
        return requestConverter.toDto(request);
    }


    public List<RequestDto> getRequests(Integer pageNo, Integer pageSize, String sortBy,String requestStatus ) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<RequestEntity> page = requestStatus != null ?
                requestRepository.findAllByRequestStatus(requestStatus, pageable) :
                requestRepository.findAll(pageable);

        return page.stream()
                .map(requestConverter::toDto)
                .collect(Collectors.toList());
    }

    public RequestDto getRequestById(Long id) {
        return requestConverter.toDto(requestRepository.getById(id));
    }

    public List<RequestDto> getRequestsByUser(Long id) {

        Long userId = userRepository.findByEmail(Utils.getCurrentEmail().orElseThrow(null)).getId();
        if (isLoggedInUser(id) || isAdmin(userId)){
            return requestRepository
                    .findByUserIdAndRequestStatusIn(id, Arrays.asList(StatusEnum.PENDING.name(), StatusEnum.ACCEPTED.name()))
                    .stream()
                    .map(requestConverter:: toDto )
                    .collect(Collectors.toList());
        }
        else {
            throw new AccessDeniedException("Access denied");
        }
    }


    public NewRequestDto createRequest(NewRequestDto newRequestDto) {
        UserEntity user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        int businessDays = getBusinessDays(newRequestDto.getFromDate(), newRequestDto.getToDate());
        for (RequestDto request : getRequestsByUser(user.getId())) {
            if (request.getRequestStatus().equals(StatusEnum.PENDING.name())) {
                throw new ActiveRequestException("Your have an pending request. Please try again later ");
            } else if (newRequestDto.getFromDate().isAfter(newRequestDto.getToDate())) {
                throw new InvalidDateException("Please enter an valid request date");
            } else if (newRequestDto.getFromDate().isAfter(newRequestDto.getToDate()) && request.getRequestStatus().equals(StatusEnum.ACCEPTED.name())) {
                throw new InvalidDateException("Yoy have merging request dates. Please check and try again");
            }
        }
        if (user.getPaidTimeOff() < businessDays) {
            throw new InsufficientPtoException("Not enough pto");
        } else if (businessDays <= 0) {
            throw new InvalidDateException("Please enter an valid date.");
        } else {
            RequestEntity requestEntity = requestConverter.toEntity(newRequestDto);
            requestEntity.setBusinessDays(businessDays);
            requestEntity.setRequestStatus(StatusEnum.PENDING.name());
            requestEntity.setDateCreated(LocalDate.now());
            requestEntity.setUser(getLoggedInUser());
            requestEntity.setDeleted(false);
            requestRepository.save(requestEntity);
            return requestConverter.toNewRequestDto(requestEntity);
        }
    }

    public RequestDto acceptRequest(Long id){
        RequestEntity request = requestRepository.getById(id);
        Integer businessDays = request.getBusinessDays();
        UserEntity employee = userRepository.getById(request.getUser().getId());
        if (employee.getPaidTimeOff() >= businessDays && Objects.equals(request.getRequestStatus(), StatusEnum.PENDING.name())) {
            employee.setPaidTimeOff(employee.getPaidTimeOff() - businessDays);
            userRepository.save(employee);
            request.setRequestStatus(StatusEnum.ACCEPTED.name());
            EmailMessage message = new EmailMessage();
            message.setTo(request.getUser().getEmail());
            message.setSubject("Request accepted");
            message.setMessage("Your request has been ACCEPTED" + "\r\n" + "Request details: " + "\r\n" +
                    "reason: " + request.getReason() + "\r\n" + "starting:  " + request.getFromDate() +
                    "\r\n" + "ending: " + request.getToDate() + "\r\n" + "Request created on " + request.getDateCreated() + "\r\n" +
                    "Have a great time :)");
            emailService.sendMail(message);
            return getRequestDto(request);
        } else {
            throw new RequestAlreadyProcessed("The request has already been processed");
        }
    }


    public NewRequestDto updateRequest(Long id, NewRequestDto requestDto) {
        if (getLoggedInUser() != requestRepository.getById(id).getUser()){
            throw new AccessDeniedException("Access denied");
        }
        else if (Objects.equals(requestRepository.getById(id).getRequestStatus(), StatusEnum.ACCEPTED.name())){
            throw new RequestAlreadyProcessed("The request has already been Accepted");
        }
        else if (Objects.equals(requestRepository.getById(id).getRequestStatus(), StatusEnum.REJECTED.name())){
            throw new RequestAlreadyProcessed("The request has already been Rejected");
        }
        else {
            RequestEntity requestEntity = requestConverter.toEntity(requestDto);
            requestEntity.setId(id);
            requestEntity.setBusinessDays(requestRepository.getById(id).getBusinessDays());
            requestEntity.setRequestStatus(StatusEnum.PENDING.name());
            requestEntity.setDateCreated(requestRepository.getById(id).getDateCreated());
            requestEntity.setUser(requestRepository.getById(id).getUser());
            requestEntity.setDeleted(false);
            requestRepository.save(requestEntity);
            return requestDto;
        }
    }

    public RequestDto rejectRequest(Long id) {
        RequestEntity request = requestRepository.getById(id);
        if (request.getRequestStatus().equals(StatusEnum.PENDING.name())) {
            request.setRequestStatus(StatusEnum.REJECTED.name());
            EmailMessage message = new EmailMessage();
            message.setTo(request.getUser().getEmail());
            message.setSubject("Request rejected");
            message.setMessage("Your request has been REJECTED" + "\r\n" + "Request details: " + "\r\n" +
                    "reason: " + request.getReason() + "\r\n" + "starting:  " + request.getFromDate() +
                    "\r\n" + "ending: " + request.getToDate() + "\r\n" + "Request created on " + request.getDateCreated());
            emailService.sendMail(message);
            return getRequestDto(request);
        } else {
            throw new RequestAlreadyProcessed("The request has already been processed");
        }
    }

    public boolean deleteRequest(Long id) {
        RequestEntity requestEntity = requestRepository.getById(id);
        requestEntity.setDeleted(true);
        requestRepository.save(requestEntity);
        return true;
    }


    private UserEntity getLoggedInUser(){
        String email = Utils.getCurrentEmail().orElse(null);
        return (userRepository.getById(userRepository.findByEmail(email).getId()));

    }

    private int getBusinessDays(LocalDate from, LocalDate to) {
        int businessDays = 0;
        for (LocalDate date = from; date.isBefore(to); date = date.plusDays(1)) {
            if (!(isWeekendDay(date) || isHoliday(date))) {
                businessDays++;
            }
        }
        return businessDays;
    }

    private boolean isWeekendDay(LocalDate date) {
        return (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY);
    }

    private boolean isHoliday(LocalDate date) {
        return holidays.contains(date);
    }


    private boolean isLoggedInUser(Long id) {
        Optional<String> optionalMail = Utils.getCurrentEmail();
        if(optionalMail.isPresent()) {
            UserEntity user = userRepository.findByEmail(optionalMail.get());
            return id.equals(user.getId());
        }
        return false;
    }
    private boolean isAdmin(Long id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return RoleEnum.ADMIN.name().equalsIgnoreCase(userEntity.getRole().getName());

    }

    @PostConstruct
    private void populateHolidays() {
        holidays = holidayRepository.findAll()
                .stream()
                .map(HolidayEntity::getDate)
                .collect(Collectors.toList());
    }
}
