package Kangcrew.kangaroo_tine.global.security.application;

import Kangcrew.kangaroo_tine.global.security.userDetail.KangarootineUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("인증 정보가 존재하지 않습니다.");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof KangarootineUserDetails userDetails) {
            return userDetails.getDatabaseId();
        }

        throw new IllegalStateException("인증된 사용자 정보를 가져올 수 없습니다.");
    }
}
