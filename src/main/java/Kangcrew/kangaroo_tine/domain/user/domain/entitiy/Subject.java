package Kangcrew.kangaroo_tine.domain.user.domain.entitiy;

import Kangcrew.kangaroo_tine.domain.user.domain.SubjectStatus;
import Kangcrew.kangaroo_tine.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Subject extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guardian_id", nullable = false)
    private Guardian guardian;

    @Column(length = 7, nullable = false)
    private String connectCode;

    @Column(length = 15, nullable = false)
    private String phone;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 30)
    private String email;

    private String profileImg;

    private LocalDate birth;

    private Boolean pushNotification;

    @Enumerated(EnumType.STRING)
    private SubjectStatus status;

    private Boolean isDeleted;
}
