package Kangcrew.kangaroo_tine.domain.mission.api;

import Kangcrew.kangaroo_tine.domain.mission.application.MissionService;
import Kangcrew.kangaroo_tine.domain.mission.dto.MissionRequestDTO;
import Kangcrew.kangaroo_tine.domain.mission.dto.MissionTodayResponseDTO;
import Kangcrew.kangaroo_tine.domain.mission.exception.MissionException;
import Kangcrew.kangaroo_tine.domain.mission.exception.MissionNotFoundException;
import Kangcrew.kangaroo_tine.domain.mission.exception.SubjectNotFoundException;
import Kangcrew.kangaroo_tine.global.config.response.BaseResponse;
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
        try {
            MissionRequestDTO response = missionService.getTodayMissionStatus(subjectId);
            return ResponseEntity.ok(new BaseResponse<>(true, "MISSION_200", "오늘 미션 현황 조회 성공", response));
        } catch (SubjectNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse<>(false, "SUBJECT_404", "존재하지 않는 대상자입니다.",null));
        } catch (MissionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BaseResponse<>(false, "MISSION_404", "해당 대상자의 오늘 미션 정보가 존재하지 않습니다.",null));
        }
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
}
