package al.ikubinfo.hrmanagement.repository;

import al.ikubinfo.hrmanagement.entity.HolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends JpaRepository<HolidayEntity, Long> {
}
