package al.ikubinfo.hrmanagement.controller;


import al.ikubinfo.hrmanagement.dto.departmentdtos.DepartmentDto;
import al.ikubinfo.hrmanagement.dto.userdtos.UserDto;
import al.ikubinfo.hrmanagement.entity.UserEntity;
import al.ikubinfo.hrmanagement.exception.AccessNotGranted;
import al.ikubinfo.hrmanagement.repository.DepartmentRepository;
import al.ikubinfo.hrmanagement.repository.UserRepository;
import al.ikubinfo.hrmanagement.services.DepartmentService;
import al.ikubinfo.hrmanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;


    @PreAuthorize("hasAnyAuthority('ADMIN','PD')")
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(departmentService.getDepartmentById(id), HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN','PD')")
    @GetMapping()
    public ResponseEntity<List<DepartmentDto>> getDepartments(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(departmentService.getDepartment());
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @GetMapping("department/{id}")
    public ResponseEntity<List<UserDto>> getUsersByDepartment(
            @PathVariable("id") Long departmentId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(userService.getUsersByDepartmentId(departmentId, pageNo, pageSize, sortBy));
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping()
    public ResponseEntity<DepartmentDto> addDepartment(@RequestBody DepartmentDto departmentDto) {
        return ResponseEntity.ok(departmentService.addDepartment(departmentDto));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping(path = "{id}")
    public ResponseEntity<DepartmentDto> deleteDepartment(@PathVariable("id") Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','PD')")
    @PutMapping(path = "{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestBody DepartmentDto departmentDto) {
        return ResponseEntity.ok(departmentService.updateDepartment(departmentDto));
    }

}
