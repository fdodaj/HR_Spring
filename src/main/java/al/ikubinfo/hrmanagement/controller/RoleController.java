package al.ikubinfo.hrmanagement.controller;


import al.ikubinfo.hrmanagement.dto.RoleDto;
import al.ikubinfo.hrmanagement.services.RoleService;
import al.ikubinfo.hrmanagement.services.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<RoleDto>> getRoles() {
        return ResponseEntity.ok(roleService.getRole());
    }

    @PostMapping("/add")
    public ResponseEntity<RoleDto> addRole(@RequestBody RoleDto roleDto){
        return ResponseEntity.ok(roleService.addRole(roleDto));
    }

    @DeleteMapping(path = "{id}")
     public ResponseEntity<RoleDto> deleteRole(@PathVariable("id") Long id){
        roleService.deleteRole(id);
        return new ResponseEntity("Role deleted", HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<RoleDto> updateRole(@RequestBody RoleDto roleDto){
        return ResponseEntity.ok(roleService.updateRole(roleDto));
    }

    @PostMapping("/addToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleRoUserForm form){
        userService.addRoleToUser(form.getEmail(), form.getRoleName());
        return ResponseEntity.ok().build();
    }
}

@Data
class RoleRoUserForm{
    private String email;
    private String roleName;
}

