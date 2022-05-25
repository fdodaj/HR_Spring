package al.ikubinfo.hrmanagement.services;

import al.ikubinfo.hrmanagement.Exception.RequestAlreadyProcessed;
import al.ikubinfo.hrmanagement.converters.RequestConverter;
import al.ikubinfo.hrmanagement.converters.UserConverter;
import al.ikubinfo.hrmanagement.dto.EmailMessage;
import al.ikubinfo.hrmanagement.dto.RequestDto;
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

    public List<RequestDto> getRequestsByUser(Long id){
        UserEntity user = userRepository.getById(id);
        return requestRepository
                .findAllByUserId(user.getId())
                .stream()
                .map(requestConverter::toDto)
                .collect(Collectors.toList());
    }

    public RequestDto createRequest(RequestDto requestDto) {
        UserEntity user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Integer businessDays = getBusinessDays(requestDto.getFromDate(), requestDto.getToDate());
        if (user.getPaidTimeOff() < businessDays) {
            System.out.println("You dont have enough PTO");
            System.out.println("your request has " + getBusinessDays(requestDto.getFromDate(), requestDto.getToDate()) + " business days");
            System.out.println("Remaining PTOs: " + user.getPaidTimeOff());
            return requestDto;
        } else {
            requestDto.setBusinessDays(businessDays);
            requestDto.setRequestStatus("Pending");
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
        if (employee.getPaidTimeOff() >= businessDays && Objects.equals(request.getRequestStatus(), "Pending")) {
            employee.setPaidTimeOff(employee.getPaidTimeOff() - businessDays);
            userRepository.save(employee);
            request.setRequestStatus("Accepted");
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
        if (request.getRequestStatus().equals("Pending")) {
            request.setRequestStatus("Rejected");
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
