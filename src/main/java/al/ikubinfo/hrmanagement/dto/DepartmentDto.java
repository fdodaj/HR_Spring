package al.ikubinfo.hrmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartmentDto {
    private Long id;

    private  String name;

    private String description;

    private String departmentLeader;

    private Boolean deleted;

}
