package Kangcrew.kangaroo_tine.domain.mission.dto;

import Kangcrew.kangaroo_tine.domain.mission.enums.MissionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class MissionTodayResponseDTO {
    private Long id;
    private LocalTime time;
    private String title;
    private String description;
    private MissionStatus status;
    private boolean isCertified; //사진 인증 완료되었는지 여부
}
