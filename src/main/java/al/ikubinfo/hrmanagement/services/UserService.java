package al.ikubinfo.hrmanagement.services;

import al.ikubinfo.hrmanagement.converters.UserConverter;
import al.ikubinfo.hrmanagement.dto.requestdtos.RequestDto;
import al.ikubinfo.hrmanagement.dto.userdtos.NewUserDto;
import al.ikubinfo.hrmanagement.dto.userdtos.UserDto;
import al.ikubinfo.hrmanagement.entity.UserEntity;
import al.ikubinfo.hrmanagement.exception.AccessNotGranted;
import al.ikubinfo.hrmanagement.exception.ActiveRequestException;
import al.ikubinfo.hrmanagement.repository.RoleRepository;
import al.ikubinfo.hrmanagement.repository.UserRepository;
import al.ikubinfo.hrmanagement.security.RoleEnum;
import al.ikubinfo.hrmanagement.security.TokenProvider;
import al.ikubinfo.hrmanagement.security.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
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
    private UserConverter userConverter;


    @Autowired
    private TokenProvider tokenProvider;


    public NewUserDto updateUser(NewUserDto userDto, Long id) {
        UserEntity userEntity = userConverter.toMinimalEntity(userDto);
        userEntity.setId(id);
        userRepository.save(userEntity);
        return userDto;
    }

    public NewUserDto addUser(NewUserDto userDto) {
        UserEntity userEntity = userConverter.toMinimalEntity(userDto);
        userRepository.save(userEntity);
        return userDto;
    }


    public List<UserDto>getUsers(Integer pageNo, Integer pageSize, String sortBy, Long roleId ) {


        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<UserEntity> page = roleId != null ?
                userRepository.findAllByRoleId(roleId, pageable) :
                userRepository.findAll(pageable);

        return page
                .stream()
                .map(userConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<UserDto>getUsersByDepartmentId(Long departmentId, Integer pageNo, Integer pageSize, String sortBy ) {
            Long userId = userRepository.findByEmail(Utils.getCurrentEmail().orElseThrow(null)).getId();

            if (isAdmin(userId) || Objects.equals(userId, departmentId)){
                Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
                Page<UserEntity> page = userRepository.getAllByDepartmentId(departmentId, pageable);

                return page
                        .stream()
                        .map(userConverter::toDto)
                        .collect(Collectors.toList());
            }


            else {
                throw new AccessNotGranted("Access not granted");
            }
    }


    public List<UserEntity> getAllUsersWithTenPtoOrLower() {
        TypedQuery<UserEntity> query = entityManager.createNamedQuery("User.findAllWithLowerPtoThanTen", UserEntity.class);
        return query.getResultList();
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

    public UserDto getPersonalInfo() {
        Long userId = userRepository.findByEmail(Utils.getCurrentEmail().orElseThrow(null)).getId();
        return userConverter.toDto(userRepository.findById(userId).orElseThrow(null));
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
