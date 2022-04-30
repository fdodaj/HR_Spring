package al.ikubinfo.hrmanagement.controller;


import al.ikubinfo.hrmanagement.dto.RoleDto;
import al.ikubinfo.hrmanagement.services.RoleService;
import al.ikubinfo.hrmanagement.services.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "basicAuth", // can be set to anything
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@OpenAPIDefinition(info = @Info(title = "Sample API", version = "v1"))


@RestController
@RequestMapping(path = "/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    private UserService userService;

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @GetMapping("/all")
    public ResponseEntity<List<RoleDto>> getRoles() {
        return ResponseEntity.ok(roleService.getRole());
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PostMapping("/add")
    public ResponseEntity<RoleDto> addRole(@RequestBody RoleDto roleDto) {
        return ResponseEntity.ok(roleService.addRole(roleDto));
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @DeleteMapping(path = "{id}")
    public ResponseEntity<RoleDto> deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
        return new ResponseEntity("Role deleted", HttpStatus.NO_CONTENT);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PutMapping(path = "{id}")
    public ResponseEntity<RoleDto> updateRole(@RequestBody RoleDto roleDto) {
        return ResponseEntity.ok(roleService.updateRole(roleDto));
    }
}


