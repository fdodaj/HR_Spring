package al.ikubinfo.hrmanagement.repository;

import al.ikubinfo.hrmanagement.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
        RoleEntity findByName(String name);
}
