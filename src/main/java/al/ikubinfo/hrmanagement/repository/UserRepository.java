package al.ikubinfo.hrmanagement.repository;

import al.ikubinfo.hrmanagement.entity.RequestEntity;
import al.ikubinfo.hrmanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);

}
