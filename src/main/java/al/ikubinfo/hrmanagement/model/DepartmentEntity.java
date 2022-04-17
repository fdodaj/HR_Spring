package al.ikubinfo.hrmanagement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Where(clause = "is_deleted=0")
@Table(name = "department")
@Getter
@Setter
@NoArgsConstructor
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "deleted")
    private Boolean deleted = Boolean.FALSE;

    @ManyToMany
    @JoinTable(name = "user_department", joinColumns = @JoinColumn(name = "department_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> userEntityDepartment = new HashSet<>();



}
