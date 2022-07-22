package al.ikubinfo.hrmanagement.services;

import al.ikubinfo.hrmanagement.converters.UserConverter;
import al.ikubinfo.hrmanagement.dto.userdtos.NewUserDto;
import al.ikubinfo.hrmanagement.dto.userdtos.UserDto;
import al.ikubinfo.hrmanagement.entity.UserEntity;
import al.ikubinfo.hrmanagement.exception.AccessNotGranted;
import al.ikubinfo.hrmanagement.repository.RoleRepository;
import al.ikubinfo.hrmanagement.repository.UserRepository;
import al.ikubinfo.hrmanagement.security.RoleEnum;
import al.ikubinfo.hrmanagement.security.TokenProvider;
import al.ikubinfo.hrmanagement.security.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private TokenProvider tokenProvider;





    public List<UserDto>getUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(userConverter::toDto)
                .collect(Collectors.toList());
    }


    public List<UserEntity> getAllUsersWithTenPtoOrLower() {
        TypedQuery<UserEntity> query = entityManager.createNamedQuery("User.findAllWithLowerPtoThanTen", UserEntity.class);
        return query.getResultList();
    }

    public NewUserDto addUser(NewUserDto userDto) {
        UserEntity userEntity = userConverter.toMinimalEntity(userDto);
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
        Long userId = userRepository.findByEmail(Utils.getCurrentEmail().orElseThrow(null)).getId();
        if (isLoggedInUser(id) || isAdmin(userId)){
            return userConverter.toDto(userRepository.findById(id).orElseThrow(null));
        }
        else {
            throw new AccessDeniedException("Access denied");
        }
    }

    public UserDto updateUser(UserDto userDto) {
        UserEntity userEntity = userConverter.toEntity(userDto);
        userRepository.save(userEntity);
        return userDto;
    }

    private boolean isLoggedInUser(Long id) {
        UserEntity user = userRepository.findByEmail(Utils.getCurrentEmail().orElseThrow(null));
        return id.equals(user.getId());
    }

    private boolean isAdmin(Long id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return RoleEnum.ADMIN.name().equalsIgnoreCase(userEntity.getRole().getName());

    }

    public TokenProvider getTokenProvider() {
        return tokenProvider;
    }

}
