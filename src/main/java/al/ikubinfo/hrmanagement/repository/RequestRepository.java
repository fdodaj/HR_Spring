package al.ikubinfo.hrmanagement.repository;

import al.ikubinfo.hrmanagement.entity.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Long> {
    List<RequestEntity> findAllByUserId(Long id);
    List<RequestEntity> findByUserIdAndRequestStatusIn(Long id, List<String> statuses);
}
