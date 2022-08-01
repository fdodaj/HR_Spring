package al.ikubinfo.hrmanagement.services;

import al.ikubinfo.hrmanagement.converters.UserConverter;
import al.ikubinfo.hrmanagement.dto.requestdtos.NewRequestDto;
import al.ikubinfo.hrmanagement.dto.requestdtos.RequestDto;
import al.ikubinfo.hrmanagement.dto.requestdtos.StatusEnum;
import al.ikubinfo.hrmanagement.dto.userdtos.MinimalUserDto;
import al.ikubinfo.hrmanagement.exception.ActiveRequestException;
import al.ikubinfo.hrmanagement.exception.InsufficientPtoException;
import al.ikubinfo.hrmanagement.exception.InvalidDateException;
import al.ikubinfo.hrmanagement.exception.RequestAlreadyProcessed;
import al.ikubinfo.hrmanagement.converters.RequestConverter;
import al.ikubinfo.hrmanagement.entity.HolidayEntity;
import al.ikubinfo.hrmanagement.entity.RequestEntity;
import al.ikubinfo.hrmanagement.entity.UserEntity;
import al.ikubinfo.hrmanagement.repository.HolidayRepository;
import al.ikubinfo.hrmanagement.repository.RequestRepository;
import al.ikubinfo.hrmanagement.repository.UserRepository;
import al.ikubinfo.hrmanagement.security.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
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

    // @Autowired
    // private EmailService emailService;

    @Autowired
    private UserConverter userConverter;

    private List<LocalDate> holidays;

    public RequestDto getRequestDto(RequestEntity request) {
        return requestConverter.toDto(request);
    }


    public List<RequestEntity> getRequests(Integer pageNo, Integer pageSize, String sortBy,String requestStatus ) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<RequestEntity> page = requestStatus != null ?
                requestRepository.findAllByRequestStatus(requestStatus, pageable) :
                requestRepository.findAll(pageable);

        return page.stream()
                .collect(Collectors.toList());
    }

    public List<RequestDto> getActiveRequests(Long id) {

        return requestRepository
                .findByUserIdAndRequestStatusIn(id, Arrays.asList(StatusEnum.PENDING.name(), StatusEnum.ACCEPTED.name()))
                .stream()
                .map(requestConverter:: toDto )
                .collect(Collectors.toList());
    }

    public NewRequestDto createRequest(NewRequestDto newRequestDto) {
        UserEntity user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        int businessDays = getBusinessDays(newRequestDto.getFromDate(), newRequestDto.getToDate());
        for (RequestDto request : getActiveRequests(user.getId())) {
            if (request.getRequestStatus().equals(StatusEnum.PENDING.name())) {
                throw new ActiveRequestException("Your request is being processed. Please wait");
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
//            requestDto.setBusinessDays(businessDays);
//            requestDto.setRequestStatus(StatusEnum.PENDING.name());
//            requestDto.setDateCreated(LocalDate.now());
//            requestDto.setUser(getLoggedInUser());
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

    public RequestDto acceptRequest(Long id) throws RequestAlreadyProcessed {
        RequestEntity request = requestRepository.getById(id);
        Integer businessDays = request.getBusinessDays();
        UserEntity employee = userRepository.getById(request.getUser().getId());
        if (employee.getPaidTimeOff() >= businessDays && Objects.equals(request.getRequestStatus(), StatusEnum.PENDING.name())) {
            employee.setPaidTimeOff(employee.getPaidTimeOff() - businessDays);
            userRepository.save(employee);
            request.setRequestStatus(StatusEnum.ACCEPTED.name());
//            EmailMessage message = new EmailMessage();
//            message.setTo(request.getUser().getEmail());
//            message.setSubject("Request accepted");
//            message.setMessage("Your request has been ACCEPTED" + "\r\n" + "Request details: " + "\r\n" +
//                    "reason: " + request.getReason() + "\r\n" + "starting:  " + request.getFromDate() +
//                    "\r\n" + "ending: " + request.getToDate() + "\r\n" + "Request created on " + request.getDateCreated() + "\r\n" +
//                    "Have a great time :)");
//            emailService.sendMail(message);
            return getRequestDto(request);
        } else {
            throw new RequestAlreadyProcessed("The request has already been processed");
        }
    }

    public boolean deleteRequest(Long id) {
        RequestEntity requestEntity = requestRepository.getById(id);
        requestEntity.setDeleted(true); // soft delete
        requestRepository.save(requestEntity);
        return true;
    }

    public RequestDto updateRequest(RequestDto requestDto) {
        if (!isLoggedInUser(requestDto.getUser().getId())) {
            throw new AccessDeniedException("Access denied");
        }
        RequestEntity requestEntity = requestConverter.toEntity(requestDto);
        requestDto.setUser(userConverter.toMinimalUserDto(getLoggedInUser()));
        requestRepository.save(requestEntity);
        return requestDto;
    }



    private UserEntity getLoggedInUser(){
        String email = Utils.getCurrentEmail().orElse(null);
        return (userRepository.getById(userRepository.findByEmail(email).getId()));

    }
    public RequestDto rejectRequest(Long id) throws RequestAlreadyProcessed {
        RequestEntity request = requestRepository.getById(id);
        if (request.getRequestStatus().equals(StatusEnum.PENDING.name())) {
            request.setRequestStatus(StatusEnum.REJECTED.name());
//            EmailMessage message = new EmailMessage();
//            message.setTo(request.getUser().getEmail());
//            message.setSubject("Request rejected");
//            message.setMessage("Your request has been REJECTED" + "\r\n" + "Request details: " + "\r\n" +
//                    "reason: " + request.getReason() + "\r\n" + "starting:  " + request.getFromDate() +
//                    "\r\n" + "ending: " + request.getToDate() + "\r\n" + "Request created on " + request.getDateCreated());
//            emailService.sendMail(message);
            return getRequestDto(request);
        } else {
            throw new RequestAlreadyProcessed("The request has already been processed");
        }
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

    @PostConstruct
    private void populateHolidays() {
        holidays = holidayRepository.findAll()
                .stream()
                .map(HolidayEntity::getDate)
                .collect(Collectors.toList());
    }
}
