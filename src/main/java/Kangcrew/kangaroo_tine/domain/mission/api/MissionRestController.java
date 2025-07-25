package Kangcrew.kangaroo_tine.domain.mission.api;

import Kangcrew.kangaroo_tine.domain.mission.application.MissionService;
import Kangcrew.kangaroo_tine.domain.mission.dto.MissionRequestDTO;
import Kangcrew.kangaroo_tine.domain.mission.dto.MissionResponse;
import Kangcrew.kangaroo_tine.domain.mission.dto.MissionTodayResponseDTO;
import Kangcrew.kangaroo_tine.domain.mission.exception.MissionNotFoundException;
import Kangcrew.kangaroo_tine.global.config.response.BaseResponse;
import Kangcrew.kangaroo_tine.global.config.response.MissionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subjects")
public class MissionRestController {

    private final MissionService missionService;

    //오늘 미션 현황 조회
    @GetMapping("/{subjectId}/mission/status")
    public ResponseEntity<?> getTodayMissionStatus(@PathVariable Long subjectId) {
        MissionRequestDTO response = missionService.getTodayMissionStatus(subjectId);
        return ResponseEntity.ok(new BaseResponse<>(true,"Mission_200","오늘 미션 현황 조회 성공", response));
    }

    //오늘 미션 목록 조회
    @GetMapping("/{subjectId}/missions/today")
    public ResponseEntity<?> getTodayMissionsBySubjectId(@PathVariable Long subjectId) {
        List<MissionTodayResponseDTO> missions = missionService.getTodayMissionsBySubjectId(subjectId);

        if (missions.isEmpty()) {
            // 미션 없을 경우 204 No Content로 응답
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new BaseResponse<>(true, "MISSION_204", "오늘 등록된 미션이 없습니다.", missions));
        }

        return ResponseEntity.ok(
                new BaseResponse<>(true, "MISSION_200", "오늘 미션 목록 조회 성공", missions)
        );
    }

    //현재 시간 기준 수행해야할 미션 조회
    @GetMapping("/{subjectId}/missions/current")
    public ResponseEntity<BaseResponse<MissionResponse.CurrentMissionDto>> getCurrentMission(
            @PathVariable Long subjectId) {
        MissionResponse.CurrentMissionDto result = missionService.getCurrentMissionBySubjectId(subjectId);
        return ResponseEntity.ok(new BaseResponse<>(true, MissionCode.MISSION_200.getCode(), MissionCode.MISSION_200.getMessage(), result));
    }
}
