package Kangcrew.kangaroo_tine.domain.mission.domain.entity;

import Kangcrew.kangaroo_tine.domain.mission.enums.Importance;
import Kangcrew.kangaroo_tine.domain.mission.enums.MissionStatus;
import Kangcrew.kangaroo_tine.domain.mission.enums.RepeatDay;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Entity
public class Mission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long guardianId;

    @Column(nullable = false)
    private Long subjectId;

    @Column(nullable = false, length = 50)
    private String title;

    private String description;

    @Column(nullable = false)
    private boolean requiresPhoto;

    @Column(nullable = false)
    private LocalTime missionStartTime;

    @Column(nullable = false)
    private LocalTime missionEndTime;

    @Column(nullable = false)
    private boolean isRepeated;

    @Enumerated(EnumType.STRING)
    private RepeatDay repeatDay;

    @Column(nullable = false)
    private LocalDate missionDate;

    private String missionMonth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Importance importance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MissionStatus status;

    private LocalTime missionCompletedTime;
}
