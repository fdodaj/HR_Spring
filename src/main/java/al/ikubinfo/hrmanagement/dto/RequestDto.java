package al.ikubinfo.hrmanagement.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

@Getter
@Setter
public class RequestDto {
    private Long id;
    private String reason;
    private Date fromDate;
    private Date toDate;
    private Integer businessDays;
    private Date dateCreated;
    private boolean deleted;


}
