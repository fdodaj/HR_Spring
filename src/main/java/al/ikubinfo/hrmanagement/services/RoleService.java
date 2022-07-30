package al.ikubinfo.hrmanagement.services;

import al.ikubinfo.hrmanagement.converters.RoleConverter;
import al.ikubinfo.hrmanagement.dto.roledtos.RoleDto;
import al.ikubinfo.hrmanagement.entity.RoleEntity;
import al.ikubinfo.hrmanagement.repository.RoleRepository;
import al.ikubinfo.hrmanagement.security.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleConverter roleConverter;

    public List<RoleDto> getRole(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        return roleRepository
                .findAll(paging)
                .stream()
                .map(roleConverter::toDto)
                .collect(Collectors.toList());
    }


    public RoleDto addRole(RoleDto roleDto) {
        RoleEntity roleEntity = roleConverter.toEntity(roleDto);
        roleRepository.save(roleEntity);
        return roleDto;
    }


    public RoleDto getRoleById(Long id){
        return roleConverter.toDto(roleRepository.findById(id).orElseThrow(null));
    }
    public boolean deleteRole(Long id) {
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

    public Long getIdByName(RoleEnum roleEnum){
        return roleRepository.findByName(roleEnum.name()).getId();
    }

}
