package Kangcrew.kangaroo_tine.domain.user.converter;

import Kangcrew.kangaroo_tine.domain.user.domain.GuardianStatus;
import Kangcrew.kangaroo_tine.domain.user.domain.SubjectStatus;
import Kangcrew.kangaroo_tine.domain.user.domain.entitiy.Guardian;
import Kangcrew.kangaroo_tine.domain.user.domain.entitiy.Subject;
import Kangcrew.kangaroo_tine.domain.user.domain.entitiy.User;
import Kangcrew.kangaroo_tine.domain.user.dto.request.UserRequestDTO;

public class UserConverter {

    public static Guardian toGuardianFromUser(User user) {
        return Guardian.builder()
                .user(user)
                .phone("010-0000-0000")
                .subjectName("미등록")
                .status(GuardianStatus.ACTIVE)
                .isDeleted(false)
                .build();
    }

    public static Subject toSubjectFromUser(User user) {
        return Subject.builder()
                .user(user)
                .connectCode(null)
                .guardian(null)
                .status(SubjectStatus.ACTIVE)
                .isDeleted(false)
                .build();
    }
}
