package al.ikubinfo.hrmanagement.dto;


import al.ikubinfo.hrmanagement.entity.RoleEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private int phoneNumber;

    private Date birthday;

    private String address;

    private String gender;

    private Date hireDate;

    private int pto;

    private  String userStatus;

    private RoleEntity role;
//
//    private RequestEntity request;
//
//    private Set<Department> department;
}
