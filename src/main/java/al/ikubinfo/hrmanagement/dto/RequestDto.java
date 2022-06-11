package al.ikubinfo.hrmanagement.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class RequestDto {
    private String reason;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer businessDays;
    private String requestStatus;
    private LocalDate dateCreated;
    private boolean deleted;
    private MinimalUserDto user;




}
