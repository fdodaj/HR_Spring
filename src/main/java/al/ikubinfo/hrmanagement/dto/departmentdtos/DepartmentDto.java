package al.ikubinfo.hrmanagement.dto.departmentdtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DepartmentDto {

    @NotNull
    private Long id;

    private String name;

    private String description;

    private String departmentLeader;

    private Boolean deleted;

}
