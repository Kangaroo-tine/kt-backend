package Kangcrew.kangaroo_tine.domain.mission.domain.repository;

import Kangcrew.kangaroo_tine.domain.mission.domain.entity.Mission;
import Kangcrew.kangaroo_tine.domain.mission.enums.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findBySubjectIdAndMissionDate(Long subjectId, LocalDate missionDate);
    Optional<Mission> findFirstBySubjectIdAndMissionDateAndMissionStartTimeBeforeAndStatusNotInOrderByMissionStartTimeAsc(
            Long subjectId,
            LocalDate missionDate,
            LocalTime missionStartTime,
            List<MissionStatus> excludeStatuses
    );
}
