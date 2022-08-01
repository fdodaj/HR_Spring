package al.ikubinfo.hrmanagement.dto.requestdtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NewRequestDto {
    private String reason;
    private LocalDate fromDate;
    private LocalDate toDate;
}
