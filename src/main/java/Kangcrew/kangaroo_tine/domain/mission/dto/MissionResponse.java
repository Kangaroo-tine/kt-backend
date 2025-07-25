package Kangcrew.kangaroo_tine.domain.mission.dto;

import Kangcrew.kangaroo_tine.domain.mission.domain.entity.Mission;
import Kangcrew.kangaroo_tine.domain.mission.enums.MissionStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

public class MissionResponse {

    @Getter
    @Builder
    public static class CurrentMissionDto {
        private Long missionId;
        private String title;
        private String description;
        private boolean requiresPhoto;
        private LocalTime startTime;
        private LocalTime endTime;
        private MissionStatus status;

        public static CurrentMissionDto from(Mission mission) {
            return CurrentMissionDto.builder()
                    .missionId(mission.getId())
                    .title(mission.getTitle())
                    .description(mission.getDescription())
                    .requiresPhoto(mission.isRequiresPhoto())
                    .startTime(mission.getMissionStartTime())
                    .endTime(mission.getMissionEndTime())
                    .status(mission.getStatus())
                    .build();
        }
    }
}
