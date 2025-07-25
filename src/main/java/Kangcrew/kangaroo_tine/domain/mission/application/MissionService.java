package Kangcrew.kangaroo_tine.domain.mission.application;

import Kangcrew.kangaroo_tine.domain.mission.dto.MissionRequestDTO;
import Kangcrew.kangaroo_tine.domain.mission.dto.MissionResponse;
import Kangcrew.kangaroo_tine.domain.mission.dto.MissionTodayResponseDTO;

import java.util.List;

public interface MissionService {
    MissionRequestDTO getTodayMissionStatus(Long subjectId);
    List<MissionTodayResponseDTO> getTodayMissionsBySubjectId(Long subjectId);
    MissionResponse.CurrentMissionDto getCurrentMissionBySubjectId(Long subjectId);
}
