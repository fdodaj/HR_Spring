package al.ikubinfo.hrmanagement.services;

import al.ikubinfo.hrmanagement.converters.DepartmentConverter;
import al.ikubinfo.hrmanagement.dto.DepartmentDto;
import al.ikubinfo.hrmanagement.model.DepartmentEntity;
import al.ikubinfo.hrmanagement.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentConverter departmentConverter;

    public List<DepartmentDto> getDepartment(){
        return departmentRepository
                .findAll()
                .stream()
                .map(departmentConverter::toDto)
                .collect(Collectors.toList());
    }

    public DepartmentDto addDepartment(DepartmentDto departmentDto){
        DepartmentEntity departmentEntity = departmentConverter.toEntity(departmentDto);
        departmentRepository.save(departmentEntity);
        return departmentDto;
    }

    public boolean deleteDepartment(Long id){
        DepartmentEntity departmentEntity = departmentRepository.getById(id);
        departmentEntity.setDeleted(true);
        departmentRepository.save(departmentEntity);
        return false;
    }

    public DepartmentDto updateDepartment(DepartmentDto departmentDto){
        DepartmentEntity departmentEntity = departmentConverter.toEntity(departmentDto);
        departmentRepository.save(departmentEntity);
        return departmentDto;
    }
}
