package al.ikubinfo.hrmanagement.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {

    private Long id;

    private String name;

    private String description;

    private Boolean deleted;

}
