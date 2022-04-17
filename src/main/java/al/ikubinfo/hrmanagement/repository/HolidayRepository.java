package al.ikubinfo.hrmanagement.repository;

import al.ikubinfo.hrmanagement.model.HolidayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HolidayRepository extends JpaRepository<HolidayEntity, Long> {
}
