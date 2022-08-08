package al.ikubinfo.hrmanagement.controller;

import al.ikubinfo.hrmanagement.dto.requestdtos.NewRequestDto;
import al.ikubinfo.hrmanagement.entity.RequestEntity;
import al.ikubinfo.hrmanagement.exception.ActiveRequestException;
import al.ikubinfo.hrmanagement.exception.InsufficientPtoException;
import al.ikubinfo.hrmanagement.exception.InvalidDateException;
import al.ikubinfo.hrmanagement.exception.RequestAlreadyProcessed;
import al.ikubinfo.hrmanagement.dto.requestdtos.RequestDto;
import al.ikubinfo.hrmanagement.services.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @GetMapping()
    public ResponseEntity<List<RequestEntity>> getRequests(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(required = false) String requestStatus

            ){
        return new ResponseEntity<>(requestService.getRequests(pageNo, pageSize, sortBy ,requestStatus ),
                                     HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR', 'PD', 'EMPLOYEE')")
    @GetMapping("user/{id}")
    public ResponseEntity<List<RequestDto>> getRequestByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.getRequestsByUser(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @GetMapping("/{id}")
    public ResponseEntity<RequestDto> getRequestById(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.getRequestById(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR', 'PD', 'EMPLOYEE')")
    @PostMapping
    public ResponseEntity<NewRequestDto> addRequest(@RequestBody NewRequestDto requestDto) {
        return ResponseEntity.ok(requestService.createRequest(requestDto));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR', 'PD', 'EMPLOYEE')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteRequest(@PathVariable("id") Long id) {
        return ResponseEntity.ok(requestService.deleteRequest(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR', 'PD', 'EMPLOYEE')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<NewRequestDto> updateRequest(@PathVariable("id") Long id, @RequestBody NewRequestDto requestDto) {
        return ResponseEntity.ok(requestService.updateRequest(id, requestDto));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @PutMapping(path = "{id}/accept")
    public ResponseEntity<RequestDto> acceptRequest(@PathVariable("id") Long id) {
        return new ResponseEntity<>(requestService.acceptRequest(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @PutMapping(path = "{id}/reject")
    public ResponseEntity<RequestDto> rejectRequest(@PathVariable("id") Long id){
        return new ResponseEntity<>(requestService.rejectRequest(id), HttpStatus.OK);
    }
}
