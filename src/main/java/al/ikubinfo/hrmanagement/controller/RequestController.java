package al.ikubinfo.hrmanagement.controller;

import al.ikubinfo.hrmanagement.dto.RequestDto;
import al.ikubinfo.hrmanagement.services.RequestService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping(path = "/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @GetMapping("/all")
    public ResponseEntity<List<RequestDto>> getRequests() {
        return new ResponseEntity<>(requestService.getRequests(), HttpStatus.OK);
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PostMapping("/add")
    public ResponseEntity<RequestDto> addRequest(@RequestBody RequestDto requestDto) {
        return ResponseEntity.ok(requestService.createRequest(requestDto));
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteRequest(@PathVariable("id") Long id) {
        return ResponseEntity.ok(requestService.deleteRequest(id));
    }

    @Operation(security = @SecurityRequirement(name = "basicAuth"))
    @PutMapping(path = "/{id}")
    public ResponseEntity<RequestDto> updateRequest(@RequestBody RequestDto requestDto) {
        return ResponseEntity.ok(requestService.updateRequest(requestDto));
    }

    @PutMapping(path = "{id}/accept")
    public ResponseEntity<RequestDto> acceptRequest(@PathVariable("id") Long id) {
        return new ResponseEntity<>(requestService.acceptRequest(id), HttpStatus.OK);
    }

    @PutMapping(path = "{id}/reject")
    public ResponseEntity<RequestDto> rejectRequest(@PathVariable("id") Long id) {
        return new ResponseEntity<>(requestService.rejectRequest(id), HttpStatus.OK);
    }
}
