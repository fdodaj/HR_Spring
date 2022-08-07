package al.ikubinfo.hrmanagement.repository;
import al.ikubinfo.hrmanagement.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, PagingAndSortingRepository<UserEntity, Long>{


    UserEntity findByEmail(String email);

    Page<UserEntity> findAllByRoleId(Long roleId, Pageable pageable);
    Page<UserEntity>findAllByDepartmentId(Long departmentId, Pageable pageable);



}
