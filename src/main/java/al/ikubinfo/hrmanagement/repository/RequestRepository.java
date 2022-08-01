package al.ikubinfo.hrmanagement.repository;

import al.ikubinfo.hrmanagement.entity.RequestEntity;
import al.ikubinfo.hrmanagement.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Long>, PagingAndSortingRepository<RequestEntity, Long> {
    List<RequestEntity> findAllByUserId(Long id);
    List<RequestEntity> findByUserIdAndRequestStatusIn(Long id, List<String> statuses);

    Page<RequestEntity> findAllByRequestStatus(String requestStatus, Pageable pageable);
}
