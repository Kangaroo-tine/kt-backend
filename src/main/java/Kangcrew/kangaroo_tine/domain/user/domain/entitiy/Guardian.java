package Kangcrew.kangaroo_tine.domain.user.domain.entitiy;

import Kangcrew.kangaroo_tine.domain.user.domain.GuardianStatus;
import Kangcrew.kangaroo_tine.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Guardian extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 15)
    private String phone;

    @Column(length = 20)
    private String subjectName;

    private LocalDate birth;

    private Boolean pushNotification;

    @Enumerated(EnumType.STRING)
    private GuardianStatus status;

    private Boolean isDeleted;
}
