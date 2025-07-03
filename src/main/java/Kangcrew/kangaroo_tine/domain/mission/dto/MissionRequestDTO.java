package Kangcrew.kangaroo_tine.domain.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MissionRequestDTO {
    private LocalDate date;
    private int missionCount;
    private int missionComplete;
}
