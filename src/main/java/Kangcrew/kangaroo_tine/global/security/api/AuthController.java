package Kangcrew.kangaroo_tine.global.security.api;

import Kangcrew.kangaroo_tine.global.common.response.BaseResponse;
import Kangcrew.kangaroo_tine.global.error.code.status.SuccessStatus;
import Kangcrew.kangaroo_tine.global.security.application.AuthService;
import Kangcrew.kangaroo_tine.global.security.dto.response.AuthResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "카카오 로그인", description = "AccessToken으로 로그인 처리")
    @PostMapping("/login")
    public BaseResponse<AuthResponseDTO> login(
            @RequestHeader("Authorization") String kakaoAccessToken
    ) {
        AuthResponseDTO response = authService.login(kakaoAccessToken);
        return BaseResponse.onSuccess(SuccessStatus.LOGIN_SUCCESS, response);
    }

    @PostMapping("/refresh")
    public BaseResponse<AuthResponseDTO> refresh(@RequestParam String refreshToken) {
        AuthResponseDTO response = authService.refresh(refreshToken);
        return BaseResponse.onSuccess(SuccessStatus.REFRESH_SUCCESS, response);
    }
}
