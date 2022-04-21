package al.ikubinfo.hrmanagement.controller;

import al.ikubinfo.hrmanagement.dto.RequestDto;
import al.ikubinfo.hrmanagement.services.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/request")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/all")
    public ResponseEntity<List<RequestDto>> getRequests() {
        return new ResponseEntity<>(requestService.getRequests(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<RequestDto> addRequest(@RequestBody RequestDto requestDto) {
        return ResponseEntity.ok(requestService.createRequest(requestDto));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<RequestDto> deleteRequest(@PathVariable("id") Long id) {
        requestService.deleteRequest(id);
        return new ResponseEntity("Request Deleted", HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<RequestDto> updateRequest(@RequestBody RequestDto requestDto) {
        return ResponseEntity.ok(requestService.updateRequest(requestDto));
    }
}
