package al.ikubinfo.hrmanagement.services;


import al.ikubinfo.hrmanagement.converters.HolidayConverter;
import al.ikubinfo.hrmanagement.dto.HolidayDto;
import al.ikubinfo.hrmanagement.entity.HolidayEntity;
import al.ikubinfo.hrmanagement.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private HolidayConverter holidayConverter;

    public List<HolidayDto> getHoliday() {
        return holidayRepository
                .findAll()
                .stream()
                .map(holidayConverter::toDto)
                .collect(Collectors.toList());
    }

    public HolidayDto addHoliday(HolidayDto holidayDto) {
        HolidayEntity holidayEntity = holidayConverter.toEntity(holidayDto);
        holidayRepository.save(holidayEntity);
        return holidayDto;
    }

    public boolean deleteHoliday(Long id) {
        HolidayEntity holidayEntity = holidayRepository.getById(id);
        holidayEntity.setDeleted(true);
        holidayRepository.save(holidayEntity);
        return false;
    }

    public HolidayDto updateHoliday(HolidayDto holidayDto) {
        HolidayEntity holidayEntity = holidayConverter.toEntity(holidayDto);
        holidayRepository.save(holidayEntity);
        return holidayDto;
    }

}
