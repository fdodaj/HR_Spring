package al.ikubinfo.hrmanagement.services;

import al.ikubinfo.hrmanagement.converters.DepartmentConverter;
import al.ikubinfo.hrmanagement.dto.departmentdtos.DepartmentDto;
import al.ikubinfo.hrmanagement.entity.DepartmentEntity;
import al.ikubinfo.hrmanagement.entity.UserEntity;
import al.ikubinfo.hrmanagement.repository.DepartmentRepository;
import al.ikubinfo.hrmanagement.repository.UserRepository;
import al.ikubinfo.hrmanagement.security.RoleEnum;
import al.ikubinfo.hrmanagement.security.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class
DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentConverter departmentConverter;

    public List<DepartmentDto> getDepartment() {
        return departmentRepository
                .findAll()
                .stream()
                .map(departmentConverter::toDto)
                .collect(Collectors.toList());
    }

    public DepartmentDto getDepartmentById(Long id){
        Long userId = userRepository.findByEmail(Utils.getCurrentEmail().orElseThrow(null)).getId();
        if (isLoggedInUser(id) || isAdmin(userId)){
            return departmentConverter.toDto(departmentRepository.findById(id).orElseThrow(null));
        }
        else {
            throw new AccessDeniedException("Access denied");
        }
    }

    public DepartmentDto addDepartment(DepartmentDto departmentDto) {
        DepartmentEntity departmentEntity = departmentConverter.toEntity(departmentDto);
        departmentRepository.save(departmentEntity);
        return departmentDto;
    }

    public boolean deleteDepartment(Long id) {
        DepartmentEntity departmentEntity = departmentRepository.getById(id);
        departmentEntity.setDeleted(true);
        departmentRepository.save(departmentEntity);
        return false;
    }

    public DepartmentDto updateDepartment(DepartmentDto departmentDto) {
        DepartmentEntity departmentEntity = departmentConverter.toEntity(departmentDto);
        departmentRepository.save(departmentEntity);
        return departmentDto;
    }

    private boolean isLoggedInUser(Long id) {
        UserEntity user = userRepository.findByEmail(Utils.getCurrentEmail().orElseThrow(null));
        return id.equals(user.getId());
    }

    private boolean isAdmin(Long id){
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return RoleEnum.ADMIN.name().equalsIgnoreCase(userEntity.getRole().getName());

    }
}
