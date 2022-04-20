package al.ikubinfo.hrmanagement.services;

import al.ikubinfo.hrmanagement.converters.UserConverter;
import al.ikubinfo.hrmanagement.dto.UserDto;
import al.ikubinfo.hrmanagement.entity.RoleEntity;
import al.ikubinfo.hrmanagement.entity.UserEntity;
import al.ikubinfo.hrmanagement.repository.RoleRepository;
import al.ikubinfo.hrmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    private UserConverter userConverter;

    public List<UserDto> getUsers(){
        return userRepository
                .findAll()
                .stream()
                .map(userConverter::toDto)
                .collect(Collectors.toList());
    }

    public UserDto addUser(UserDto userDto){
        UserEntity userEntity = userConverter.toEntity(userDto);
        userRepository.save(userEntity);
        return userDto;
    }

    public boolean deleteUser(Long id){
        UserEntity userEntity = userRepository.getById(id);
        userEntity.setDeleted(true);
        userRepository.save(userEntity);
        return true;
    }



    public UserDto updateUser (UserDto userDto){
        UserEntity userEntity = userConverter.toEntity(userDto);
        userRepository.save(userEntity);
        return userDto;
    }

    void addUserToRole(String email, String roleName){
        RoleEntity role = roleRepository.findByName(roleName);
        UserEntity user = userRepository.findByEmail(email);
        user.setRole(role);
    }

}
