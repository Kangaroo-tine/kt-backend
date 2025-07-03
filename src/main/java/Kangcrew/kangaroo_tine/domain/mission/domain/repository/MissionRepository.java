package Kangcrew.kangaroo_tine.domain.mission.domain.repository;

import Kangcrew.kangaroo_tine.domain.mission.domain.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findBySubjectIdAndDate(Long subjectId, LocalDate date);
}
