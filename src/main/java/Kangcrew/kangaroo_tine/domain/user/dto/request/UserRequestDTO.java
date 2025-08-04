package Kangcrew.kangaroo_tine.domain.user.dto.request;

import Kangcrew.kangaroo_tine.domain.user.domain.UserRole;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDTO {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SetUserRoleDTO {
        @NotNull(message = "역할은 필수입니다.")
        private UserRole role;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaveGuardianPhoneDTO {
        private String phone;
    }
}
