package Kangcrew.kangaroo_tine.global.security.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponseDTO {

    private final String accessToken;
    private final String refreshToken;
    private final String accessTokenExpirationTime;
    private final String refreshTokenExpirationTime;
}
