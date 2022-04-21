package al.ikubinfo.hrmanagement.dto;


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

    private Date birthday;

    private String gender;

    private Date hireDate;

    private int paidTimeOff;

    private boolean deleted;
    //
    private Long role;

}
//
//    private RequestEntity request;
//    private Set<RequestEntity> requests = new HashSet<>();
//
//    private Set<DepartmentEntity> departmentEntity = new HashSet<>();

