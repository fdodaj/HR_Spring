package al.ikubinfo.hrmanagement.converters;


import al.ikubinfo.hrmanagement.dto.HolidayDto;
import al.ikubinfo.hrmanagement.model.HolidayEntity;
import org.springframework.stereotype.Component;

@Component
public class HolidayConverter implements BidirectionalConverter<HolidayDto, HolidayEntity> {

    @Override
    public HolidayDto toDto(HolidayEntity entity) {
        HolidayDto dto = new HolidayDto();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setDate(entity.getDate());
        dto.setActive(entity.getActive());
        dto.setDeleted(entity.getDeleted());

        return dto;
    }

    @Override
    public HolidayEntity toEntity(HolidayDto dto) {
        HolidayEntity entity = new HolidayEntity();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());
        entity.setActive(dto.getActive());
        entity.setDeleted(dto.getDeleted());

        return entity;
    }
}
