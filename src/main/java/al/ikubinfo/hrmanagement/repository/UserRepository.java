package al.ikubinfo.hrmanagement.repository;

import al.ikubinfo.hrmanagement.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
