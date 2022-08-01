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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

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


    @GetMapping("{id}")
    public ResponseEntity<List<RequestDto>> getRequestByUser(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.getActiveRequests(id));
    }

    @PostMapping
    public ResponseEntity<NewRequestDto> addRequest(@RequestBody NewRequestDto requestDto) {
        return ResponseEntity.ok(requestService.createRequest(requestDto));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteRequest(@PathVariable("id") Long id) {
        return ResponseEntity.ok(requestService.deleteRequest(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<RequestDto> updateRequest(@RequestBody RequestDto requestDto) {
        return ResponseEntity.ok(requestService.updateRequest(requestDto));
    }

    @PutMapping(path = "{id}/accept")
    public ResponseEntity<RequestDto> acceptRequest(@PathVariable("id") Long id) {
        return new ResponseEntity<>(requestService.acceptRequest(id), HttpStatus.OK);
    }

    @PutMapping(path = "{id}/reject")
    public ResponseEntity<RequestDto> rejectRequest(@PathVariable("id") Long id){
        return new ResponseEntity<>(requestService.rejectRequest(id), HttpStatus.OK);
    }
}
