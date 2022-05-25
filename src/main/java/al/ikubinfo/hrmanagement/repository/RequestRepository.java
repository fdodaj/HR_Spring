package al.ikubinfo.hrmanagement.repository;

import al.ikubinfo.hrmanagement.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
    List<RequestEntity> findAllByUserId(Long id);
}
