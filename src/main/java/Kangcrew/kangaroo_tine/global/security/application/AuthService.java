package Kangcrew.kangaroo_tine.global.security.application;

import Kangcrew.kangaroo_tine.global.security.dto.response.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO login(String kakaoAccessToken);
    AuthResponseDTO refresh(String refreshToken);
}
