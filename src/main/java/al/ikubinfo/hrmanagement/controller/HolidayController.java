package al.ikubinfo.hrmanagement.controller;

import al.ikubinfo.hrmanagement.dto.HolidayDto;
import al.ikubinfo.hrmanagement.services.HolidayService;
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
@RequestMapping(path = "/holidays")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @GetMapping("/all")
    public ResponseEntity<List<HolidayDto>> getHoliday() {
        return ResponseEntity.ok(holidayService.getHoliday());
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PostMapping("/add")
    public ResponseEntity<HolidayDto> addHoliday(@RequestBody HolidayDto holidayDto) {
        return ResponseEntity.ok(holidayService.addHoliday(holidayDto));
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @DeleteMapping(path = "{id}")
    public ResponseEntity<HolidayDto> deleteHoliday(@PathVariable("id") Long id) {
        holidayService.deleteHoliday(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PutMapping(path = "{id}")
    public ResponseEntity<HolidayDto> updateHoliday(@RequestBody HolidayDto holidayDto) {
        return ResponseEntity.ok(holidayService.updateHoliday(holidayDto));
    }
}