package Kangcrew.kangaroo_tine.domain.mission.application;

import Kangcrew.kangaroo_tine.domain.mission.domain.entity.Mission;
import Kangcrew.kangaroo_tine.domain.mission.domain.repository.MissionRepository;
import Kangcrew.kangaroo_tine.domain.mission.dto.MissionRequestDTO;
import Kangcrew.kangaroo_tine.domain.mission.dto.MissionTodayResponseDTO;
import Kangcrew.kangaroo_tine.domain.mission.enums.MissionStatus;
import Kangcrew.kangaroo_tine.domain.mission.exception.MissionException;
import Kangcrew.kangaroo_tine.domain.mission.exception.MissionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MissionServiceImpl implements MissionService{
    private final MissionRepository missionRepository;

    @Override
    public MissionRequestDTO getTodayMissionStatus(Long subjectId) {
        LocalDate today = LocalDate.now();
        List<Mission> missions = missionRepository.findBySubjectIdAndDate(subjectId, today);

        if (missions.isEmpty()) {
            throw new MissionNotFoundException(); // 커스텀 예외
        }

        int total = missions.size();
        int completed = (int) missions.stream().filter(m -> m.getStatus() == MissionStatus.COMPLETED).count();

        return new MissionRequestDTO(today, total, completed);
    }

    @Override
    public List<MissionTodayResponseDTO> getTodayMissionsBySubjectId(Long subjectId) {
        LocalDate today = LocalDate.now();

        // 예외: 존재하지 않는 대상자일 경우 처리 (SubjectService 등 필요시)
        // subjectService.validateExists(subjectId);

        List<Mission> missions = missionRepository.findBySubjectIdAndDate(subjectId, today);

        // 정렬 (시작 시간 순)
        return missions.stream()
                .sorted(Comparator.comparing(Mission::getMissionStartTime))
                .map(m -> new MissionTodayResponseDTO(
                        m.getId(),
                        m.getMissionStartTime(),
                        m.getTitle(),
                        m.getDescription(),
                        m.getStatus(),
                        m.isRequiresPhoto()
                ))
                .collect(Collectors.toList());
    }

}
