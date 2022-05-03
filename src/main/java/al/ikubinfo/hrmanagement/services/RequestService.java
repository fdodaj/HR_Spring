package al.ikubinfo.hrmanagement.services;

import al.ikubinfo.hrmanagement.converters.RequestConverter;
import al.ikubinfo.hrmanagement.converters.UserConverter;
import al.ikubinfo.hrmanagement.dto.HolidayDto;
import al.ikubinfo.hrmanagement.dto.RequestDto;
import al.ikubinfo.hrmanagement.dto.UserDto;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;
import java.nio.file.AccessDeniedException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
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

    public RequestDto createRequest(RequestDto requestDto) {
        UserEntity user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Integer businessDays = getBusinessDays(requestDto.getFromDate(), requestDto.getToDate());
        if (user.getPaidTimeOff() < businessDays ){
            System.out.println("You dont have enough PTO");
            System.out.println("your request has " + getBusinessDays(requestDto.getFromDate(), requestDto.getToDate())+ " business days");
            System.out.println("Remaining PTOs: " + user.getPaidTimeOff());
            return  requestDto;
        }
        else{
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
        RequestEntity requestEntity = requestConverter.toEntity(requestDto);
        requestRepository.save(requestEntity);
        return requestDto;
    }

    public RequestDto acceptRequest(Long id) {
        RequestEntity request = requestRepository.getOne(id);
        UserEntity pm = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        Integer businessDays = request.getBusinessDays();
        UserEntity employee = userRepository.getById(request.getId());
        if (employee.getPaidTimeOff() >= businessDays) {
            employee.setPaidTimeOff(employee.getPaidTimeOff() - businessDays);
            userRepository.save(employee);
            request.setRequestStatus("ACCEPTED");
            return getRequestDto(request);
        } else {
            return getRequestDto(request);
        }
     }

    public RequestDto rejectRequest(Long id) {
        RequestEntity request = requestRepository.getById(id);
        request.setRequestStatus("Rejected");
        return getRequestDto(request);
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

     private boolean isWeekendDay(LocalDate date){
         return (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY);
     }

     private boolean isHoliday(LocalDate date){
        int counter  = 0;
        for (HolidayEntity holiday : holidayRepository.findAll()){
            if (holiday.getDate().equals(date)){
                counter++;
            }
        }
        return counter > 0;
     }
    }
