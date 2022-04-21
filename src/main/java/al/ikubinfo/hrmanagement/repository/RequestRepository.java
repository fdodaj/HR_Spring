package al.ikubinfo.hrmanagement.repository;

import al.ikubinfo.hrmanagement.model.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository  extends JpaRepository<RequestEntity, Long> {
}
