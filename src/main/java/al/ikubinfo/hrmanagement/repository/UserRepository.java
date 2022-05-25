package al.ikubinfo.hrmanagement.repository;

import al.ikubinfo.hrmanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

}
