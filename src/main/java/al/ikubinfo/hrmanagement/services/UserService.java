package al.ikubinfo.hrmanagement.services;

import al.ikubinfo.hrmanagement.converters.UserConverter;
import al.ikubinfo.hrmanagement.dto.UserDto;
import al.ikubinfo.hrmanagement.entity.UserEntity;
import al.ikubinfo.hrmanagement.repository.RoleRepository;
import al.ikubinfo.hrmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserConverter userConverter;

    public List<UserDto> getUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userConverter::toDto)
                .collect(Collectors.toList());
    }

    public UserDto addUser(UserDto userDto) {
        UserEntity userEntity = userConverter.toEntity(userDto);
        userRepository.save(userEntity);
        return userDto;
    }

    public boolean deleteUser(Long id) {
        UserEntity userEntity = userRepository.getById(id);
        userEntity.setDeleted(true);
        userRepository.save(userEntity);
        return true;
    }

    public UserDto getUserById(Long id) {
        UserEntity user = userRepository.getById(id);
        return userConverter.toDto(user);
    }

    public UserDto updateUser(UserDto userDto) {
        UserEntity userEntity = userConverter.toEntity(userDto);
        userRepository.save(userEntity);
        return userDto;
    }

}
