package al.ikubinfo.hrmanagement.controller;


import al.ikubinfo.hrmanagement.dto.DepartmentDto;
import al.ikubinfo.hrmanagement.services.DepartmentService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
@RequestMapping(path = "/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @GetMapping("/all")
    public ResponseEntity<List<DepartmentDto>> getDepartments() {
        return ResponseEntity.ok(departmentService.getDepartment());
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PostMapping("/add")
    public ResponseEntity<DepartmentDto> addDepartment(@RequestBody DepartmentDto departmentDto) {
        return ResponseEntity.ok(departmentService.addDepartment(departmentDto));
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @DeleteMapping(path = "{id}")
    public ResponseEntity<DepartmentDto> deleteDepartment(@PathVariable("id") Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PutMapping(path = "{id}")
    public ResponseEntity<DepartmentDto> updateDepartment(@RequestBody DepartmentDto departmentDto) {
        return ResponseEntity.ok(departmentService.updateDepartment(departmentDto));
    }

}
