package al.ikubinfo.hrmanagement.dto;


import al.ikubinfo.hrmanagement.model.DepartmentEntity;
import al.ikubinfo.hrmanagement.model.Request;
import al.ikubinfo.hrmanagement.model.RoleEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private Date birthday;

    private String gender;

    private Date hireDate;

    private int paidTimeOff;

    private boolean deleted;
//
//    private RoleEntity role;
//
//    private Request request;
//    private Set<Request> requests = new HashSet<>();
//
//    private Set<DepartmentEntity> departmentEntity = new HashSet<>();
}
