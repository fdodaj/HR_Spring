package al.ikubinfo.hrmanagement.controller;

import al.ikubinfo.hrmanagement.dto.requestdtos.RequestDto;
import al.ikubinfo.hrmanagement.dto.userdtos.NewUserDto;
import al.ikubinfo.hrmanagement.dto.userdtos.UserDto;
import al.ikubinfo.hrmanagement.entity.UserEntity;
import al.ikubinfo.hrmanagement.exception.AccessNotGranted;
import al.ikubinfo.hrmanagement.repository.UserRepository;
import al.ikubinfo.hrmanagement.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }




    @GetMapping("/all/less-than-ten")
    public ResponseEntity<List<UserEntity>> getUsersWithLowerPtoThanTen() {
        return new ResponseEntity<>(userService.getAllUsersWithTenPtoOrLower(), HttpStatus.OK);
    }



    @PostMapping("/add")
    public ResponseEntity<NewUserDto> addUser(@RequestBody NewUserDto userDto) {
        return ResponseEntity.ok(userService.addUser(userDto));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }
}
