package al.ikubinfo.hrmanagement.dto.userdtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MinimalUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

}
