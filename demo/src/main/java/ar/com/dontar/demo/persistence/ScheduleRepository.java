package ar.com.dontar.demo.persistence;

import ar.com.dontar.demo.persistence.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
}
