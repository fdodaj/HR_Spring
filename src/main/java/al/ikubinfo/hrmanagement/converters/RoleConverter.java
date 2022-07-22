package al.ikubinfo.hrmanagement.converters;

import al.ikubinfo.hrmanagement.dto.roledtos.MinimalRoleDto;
import al.ikubinfo.hrmanagement.dto.roledtos.RoleDto;
import al.ikubinfo.hrmanagement.entity.RoleEntity;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter implements BidirectionalConverter<RoleDto, RoleEntity> {

    @Override
    public RoleDto toDto(RoleEntity entity) {

        RoleDto dto = new RoleDto();
        dto.setId(entity.getId());
        dto.setDescription(entity.getDescription());
        dto.setName(entity.getName());
        dto.setDeleted(entity.getDeleted());
        return dto;
    }

    public MinimalRoleDto toMinimalRoleDto(RoleEntity entity){
        MinimalRoleDto dto = new MinimalRoleDto();
        dto.setId(entity.getId());
        return dto;

    }

    public RoleEntity toMinimalRoleEntity(MinimalRoleDto dto){
        RoleEntity entity = new RoleEntity();
        entity.setId(dto.getId());
        return entity;

    }

    @Override
    public RoleEntity toEntity(RoleDto dto) {
        RoleEntity entity = new RoleEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDeleted(dto.getDeleted());
        return entity;
    }
}
