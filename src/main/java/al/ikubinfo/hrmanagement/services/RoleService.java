package al.ikubinfo.hrmanagement.services;

import al.ikubinfo.hrmanagement.converters.RoleConverter;
import al.ikubinfo.hrmanagement.dto.RoleDto;
import al.ikubinfo.hrmanagement.entity.RoleEntity;
import al.ikubinfo.hrmanagement.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleConverter roleConverter;

    public List<RoleDto> getRole(){


        return roleRepository
                .findAll()
                .stream()
                .map(roleConverter::toDto)
                .collect(Collectors.toList());
    }


    public RoleDto addRole(RoleDto roleDto){
            RoleEntity roleEntity = roleConverter.toEntity(roleDto);
            roleRepository.save(roleEntity);
            return roleDto;
    }

    public boolean deleteRole(Long id){
        RoleEntity roleEntity = roleRepository.getById(id);
        roleEntity.setDeleted(true);
        roleRepository.save(roleEntity);
        return false;
    }

    public RoleDto updateRole(RoleDto roleDto) {
        RoleEntity roleEntity = roleConverter.toEntity(roleDto);
        roleRepository.save(roleEntity);
        return roleDto;
    }

}
