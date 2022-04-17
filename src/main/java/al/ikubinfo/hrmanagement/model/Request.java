package al.ikubinfo.hrmanagement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "request ")
@Getter
@Setter
@NoArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private int id;

    @Column(name = "reason")
    private String reason;

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    @Column(name = "business_days")
    private Integer businessDays;

    @Column(name = "date_created")
    private Date dateCreated;

    @Column(name = "deleted")
    private Boolean Deleted;

    @OneToMany(mappedBy = "request")
    private Set<UserEntity> userEntities = new HashSet();


}
