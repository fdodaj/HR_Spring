package al.ikubinfo.hrmanagement.dto.userdtos;


import al.ikubinfo.hrmanagement.dto.departmentdtos.MinimalDepartmentDto;
import al.ikubinfo.hrmanagement.dto.roledtos.MinimalRoleDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class NewUserDto {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private LocalDate birthday;

    private String gender;

    private Long role;

    private Long department;
}

