package al.ikubinfo.hrmanagement.converters;

import al.ikubinfo.hrmanagement.dto.requestdtos.NewRequestDto;
import al.ikubinfo.hrmanagement.dto.requestdtos.RequestDto;
import al.ikubinfo.hrmanagement.entity.RequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RequestConverter implements BidirectionalConverter<RequestDto, RequestEntity> {

    @Autowired
    private UserConverter userConverter;

    @Override
    public RequestDto toDto(RequestEntity entity) {
        RequestDto dto = new RequestDto();

        dto.setReason(entity.getReason());
        dto.setFromDate(entity.getFromDate());
        dto.setToDate(entity.getToDate());
        dto.setBusinessDays(entity.getBusinessDays());
        dto.setDateCreated(entity.getDateCreated());
        dto.setDeleted(entity.getDeleted());
        dto.setRequestStatus(entity.getRequestStatus());
        dto.setUser(userConverter.toMinimalUserDto(entity.getUser()));
        return dto;
    }

    @Override
    public RequestEntity toEntity(RequestDto dto) {
        RequestEntity entity = new RequestEntity();
        entity.setReason(dto.getReason());
        entity.setFromDate(dto.getFromDate());
        entity.setToDate(dto.getToDate());
        entity.setBusinessDays(dto.getBusinessDays());
        entity.setDateCreated(dto.getDateCreated());
        entity.setDeleted(dto.isDeleted());
        entity.setToDate(dto.getToDate());
        entity.setRequestStatus(dto.getRequestStatus());
        entity.setUser(userConverter.getEntity(dto.getUser()));
        return entity;
    }
    public RequestEntity toEntity(NewRequestDto dto) {
        RequestEntity entity = new RequestEntity();
        entity.setReason(dto.getReason());
        entity.setFromDate(dto.getFromDate());
        entity.setToDate(dto.getToDate());
        return entity;
    }

    public NewRequestDto toNewRequestDto(RequestEntity entity) {
        NewRequestDto dto = new NewRequestDto();

        dto.setReason(entity.getReason());
        dto.setFromDate(entity.getFromDate());
        dto.setToDate(entity.getToDate());
        return dto;
    }



}
