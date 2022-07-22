package al.ikubinfo.hrmanagement.dto.userdtos;


import al.ikubinfo.hrmanagement.dto.departmentdtos.DepartmentDto;
import al.ikubinfo.hrmanagement.dto.roledtos.RoleDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class UserDto {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private LocalDate birthday;

    private String gender;

    private LocalDate hireDate;

    private int paidTimeOff;

    private boolean deleted;

    private RoleDto role;

    private DepartmentDto department;
}

