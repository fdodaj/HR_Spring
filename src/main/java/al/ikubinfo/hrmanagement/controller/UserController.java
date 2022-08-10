package al.ikubinfo.hrmanagement.controller;


import al.ikubinfo.hrmanagement.dto.userdtos.NewUserDto;
import al.ikubinfo.hrmanagement.dto.userdtos.UserDto;
import al.ikubinfo.hrmanagement.entity.UserEntity;
import al.ikubinfo.hrmanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "users")
public class UserController {


    @Autowired
    private UserService userService;

    
    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @GetMapping()
    public ResponseEntity<List<UserDto>> getUsers(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long roleId,
            @RequestParam(defaultValue = "id") String sortBy){
        return new ResponseEntity<>(userService.getUsers(pageNo, pageSize, sortBy, roleId),
                HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @GetMapping("/less-than-ten")
    public ResponseEntity<List<UserEntity>> getUsersWithLowerPtoThanTen() {
        return new ResponseEntity<>(userService.getAllUsersWithTenPtoOrLower(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")    @PostMapping()
    public ResponseEntity<NewUserDto> addUser(@RequestBody NewUserDto userDto) {
        return ResponseEntity.ok(userService.addUser(userDto));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR')")
    @PutMapping(path = "/{id}")
    public ResponseEntity<NewUserDto> updateUser(@RequestBody NewUserDto userDto, @PathVariable Long id) {
        return ResponseEntity.ok(userService.updateUser(userDto, id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR', 'PD', 'EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'HR', 'PD', 'EMPLOYEE')")
    @GetMapping("/me")
    public ResponseEntity<UserDto> getPersonalInfo() {
        return new ResponseEntity<>(userService.getPersonalInfo(), HttpStatus.OK);
    }
}