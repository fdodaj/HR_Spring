package al.ikubinfo.hrmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HolidayDto {
    private Long id;

    private String name;

    private String description;

    private LocalDate date;

    private Boolean active;

    private Boolean deleted;

}
