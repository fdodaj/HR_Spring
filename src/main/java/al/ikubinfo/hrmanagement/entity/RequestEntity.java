package al.ikubinfo.hrmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;


@Entity
@Where(clause = "deleted = 0")
@Table(name = "request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "reason")
    private String reason;

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    @Column(name = "business_days")
    private Integer businessDays;

    @Column(name = "request_status")
    private String requestStatus;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "deleted")
    private Boolean Deleted;

    @ManyToOne
    @JoinColumn(name = "user")
    private UserEntity user;

}
