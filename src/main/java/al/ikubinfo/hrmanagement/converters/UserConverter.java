package al.ikubinfo.hrmanagement.converters;

import al.ikubinfo.hrmanagement.dto.UserDto;
import al.ikubinfo.hrmanagement.model.UserEntity;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;


@Component
public class UserConverter implements BidirectionalConverter<UserDto, UserEntity> {

    @Override
    public UserDto toDto(UserEntity entity) {
        UserDto dto = new UserDto();

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPassword(entity.getPassword());
        dto.setBirthday(entity.getBirthday());
        dto.setGender(entity.getGender());
        dto.setHireDate(entity.getHireDate());
        dto.setPto(entity.getPto());
        dto.setUserStatus(entity.getUserStatus());
        return dto;
    }


    @Override
    public UserEntity toEntity(UserDto dto) {
        UserEntity entity = new UserEntity();

        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setBirthday(dto.getBirthday());
        entity.setGender(dto.getGender());
        entity.setHireDate(dto.getHireDate());
        entity.setPto(dto.getPto());
        entity.setUserStatus(dto.getUserStatus());
        return entity;
    }
}
