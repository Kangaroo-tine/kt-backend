package Kangcrew.kangaroo_tine.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class UserResponseDTO {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ConnectCodeDTO {
        private String connectCode;
    }
}
