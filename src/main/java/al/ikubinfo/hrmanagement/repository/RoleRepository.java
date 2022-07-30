package al.ikubinfo.hrmanagement.repository;

import al.ikubinfo.hrmanagement.entity.RoleEntity;
import al.ikubinfo.hrmanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long>, PagingAndSortingRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
    String getByName(String name);



}
