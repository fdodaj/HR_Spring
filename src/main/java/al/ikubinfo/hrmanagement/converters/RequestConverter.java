package al.ikubinfo.hrmanagement.converters;

import al.ikubinfo.hrmanagement.dto.RequestDto;
import al.ikubinfo.hrmanagement.model.RequestEntity;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.Date;

@Component
public class RequestConverter implements BidirectionalConverter<RequestDto, RequestEntity>{

    @Override
    public RequestDto toDto(RequestEntity entity){
        RequestDto dto = new RequestDto();

        dto.setId(entity.getId());
        dto.setReason(entity.getReason());
        dto.setFromDate(entity.getFromDate());
        dto.setToDate(entity.getToDate());
        dto.setBusinessDays(entity.getBusinessDays());
        dto.setDateCreated(entity.getDateCreated());
        dto.setDeleted(entity.getDeleted());
        dto.setRequestStatus(entity.getRequestStatus());
        return dto;
    }

    @Override
    public RequestEntity toEntity(RequestDto dto) {
        RequestEntity entity = new RequestEntity();
        entity.setId(dto.getId());
        entity.setReason(dto.getReason());
        entity.setFromDate(dto.getFromDate());
        entity.setBusinessDays(dto.getBusinessDays());
        entity.setDateCreated(dto.getDateCreated());
        entity.setDeleted(dto.isDeleted());
        entity.setToDate(entity.getToDate());
        entity.setRequestStatus(entity.getRequestStatus());
        return entity;
    }
}