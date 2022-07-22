package al.ikubinfo.hrmanagement.converters;

import al.ikubinfo.hrmanagement.dto.userdtos.MinimalUserDto;
import al.ikubinfo.hrmanagement.dto.userdtos.NewUserDto;
import al.ikubinfo.hrmanagement.dto.userdtos.UserDto;
import al.ikubinfo.hrmanagement.entity.UserEntity;
import al.ikubinfo.hrmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class UserConverter implements BidirectionalConverter<UserDto, UserEntity> {

    @Autowired
    private RoleConverter roleConverter;

    @Autowired
    private DepartmentConverter departmentConverter;
    @Autowired
    private UserRepository userRepository;

    @Value("${startingPto}")
    private int startingPto;

    @Override
    public UserDto toDto(UserEntity entity) {
        UserDto dto = new UserDto();

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setBirthday(entity.getBirthday());
        dto.setGender(entity.getGender());
        dto.setHireDate(entity.getHireDate());
        dto.setPaidTimeOff(entity.getPaidTimeOff());
        dto.setRole(roleConverter.toDto(entity.getRole()));
        dto.setDepartment(departmentConverter.toDto(entity.getDepartment()));
        return dto;
    }

    public MinimalUserDto toMinimalUserDto(UserEntity entity) {
        MinimalUserDto dto = new MinimalUserDto();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        return dto;
    }



    public UserEntity getEntity(MinimalUserDto dto) {
        return userRepository.getById(dto.getId());

    }


    public UserEntity ToMinimalUserDto(NewUserDto dto){
        UserEntity entity = new UserEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setBirthday(dto.getBirthday());
        entity.setGender(dto.getGender());
        entity.setRole(roleConverter.toMinimalRoleEntity(dto.getRole()));
        entity.setDepartment(departmentConverter.toMinimalDepartmentEntity(dto.getDepartment()));
        return entity;
    }

    public UserEntity toMinimalEntity(NewUserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setBirthday(dto.getBirthday());
        entity.setGender(dto.getGender());
        entity.setHireDate(LocalDate.now());
        entity.setPaidTimeOff(startingPto);
        entity.setRole(roleConverter.toMinimalRoleEntity(dto.getRole()));
        entity.setDepartment(departmentConverter.toMinimalDepartmentEntity(dto.getDepartment()));
        return entity;
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
        entity.setPaidTimeOff(dto.getPaidTimeOff());
        entity.setDeleted(dto.isDeleted());
        entity.setRole(roleConverter.toEntity(dto.getRole()));
        entity.setDepartment(departmentConverter.toEntity(dto.getDepartment()));
        return entity;
    }
}
