package al.ikubinfo.hrmanagement.services;

import al.ikubinfo.hrmanagement.Exception.ActiveRequestException;
import al.ikubinfo.hrmanagement.Exception.InsufficientPtoException;
import al.ikubinfo.hrmanagement.Exception.InvalidDateException;
import al.ikubinfo.hrmanagement.Exception.RequestAlreadyProcessed;
import al.ikubinfo.hrmanagement.converters.RequestConverter;
import al.ikubinfo.hrmanagement.converters.UserConverter;
import al.ikubinfo.hrmanagement.dto.EmailMessage;
import al.ikubinfo.hrmanagement.dto.RequestDto;
import al.ikubinfo.hrmanagement.dto.StatusEnum;
import al.ikubinfo.hrmanagement.entity.HolidayEntity;
import al.ikubinfo.hrmanagement.entity.RequestEntity;
import al.ikubinfo.hrmanagement.entity.UserEntity;
import al.ikubinfo.hrmanagement.repository.HolidayRepository;
import al.ikubinfo.hrmanagement.repository.RequestRepository;
import al.ikubinfo.hrmanagement.repository.UserRepository;
import al.ikubinfo.hrmanagement.security.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
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
    private UserConverter userConverter;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;


    public RequestDto getRequestDto(RequestEntity request) {
        return requestConverter.toDto(request);
    }


    public List<RequestDto> getRequests() {
        return requestRepository
                .findAll()
                .stream()
                .map(requestConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<RequestDto> getActiveRequests(Long id){
        return requestRepository
                .findByUserIdAndRequestStatusIn(id, Arrays.asList(StatusEnum.PENDING.name(), StatusEnum.ACCEPTED.name()))
                .stream()
                .map(requestConverter::toDto)
                .collect(Collectors.toList());
    }

    public RequestDto createRequest(RequestDto requestDto) throws ActiveRequestException, InvalidDateException, InsufficientPtoException {
        UserEntity user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Integer businessDays = getBusinessDays(requestDto.getFromDate(), requestDto.getToDate());
        for (RequestDto request : getActiveRequests(user.getId())){
            if(request.getRequestStatus().equals(StatusEnum.PENDING.name())){
                throw new ActiveRequestException("Your request is being processed. Please wait");
            }
            else if (request.getFromDate().isBefore(request.getToDate())){
                throw new InvalidDateException("Please enter an valid request date");
            }
            else if (requestDto.getFromDate().isBefore(request.getToDate()) && request.getRequestStatus().equals(StatusEnum.ACCEPTED.name())){
                throw new InvalidDateException("Request  that you made on " + request.getDateCreated() + " conflicts with request are creating. Please check and try again" );
            }

        }
        if (user.getPaidTimeOff() < businessDays) {
            throw new InsufficientPtoException("Not enough pto");
        }
        else if (businessDays<=0){
            throw new InvalidDateException("Please enter an valid date.");
        }
        else {
            requestDto.setBusinessDays(businessDays);
            requestDto.setRequestStatus(StatusEnum.PENDING.name());
            requestDto.setDateCreated(LocalDate.now());
            RequestEntity requestEntity = requestConverter.toEntity(requestDto);
            requestRepository.save(requestEntity);
            return getRequestDto(requestEntity);
        }
    }


    public boolean deleteRequest(Long id) {
        RequestEntity requestEntity = requestRepository.getById(id);
        requestEntity.setDeleted(true);
        requestRepository.save(requestEntity);
        return true;
    }

    public RequestDto updateRequest(RequestDto requestDto) {
        if (!isLoggedInUser(requestDto.getUser().getId())) {
            throw new AccessDeniedException("Access denied");
        }
        RequestEntity requestEntity = requestConverter.toEntity(requestDto);
        requestRepository.save(requestEntity);
        return requestDto;
    }

    public RequestDto acceptRequest(Long id) throws RequestAlreadyProcessed {
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

    public RequestDto rejectRequest(Long id) throws RequestAlreadyProcessed {
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
        int counter = 0;
        for (HolidayEntity holiday : holidayRepository.findAll()) {
            if (holiday.getDate().equals(date)) {
                counter++;
            }
        }
        return counter > 0;
    }


    private boolean isLoggedInUser(Long id) {
        UserEntity user = userRepository.findByEmail(Utils.getCurrentEmail().get());
        return id.equals(user.getId());
    }
}
