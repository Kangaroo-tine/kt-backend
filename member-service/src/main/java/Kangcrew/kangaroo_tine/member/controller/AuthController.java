package Kangcrew.kangaroo_tine.member.controller;

import Kangcrew.kangaroo_tine.member.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/kakao")
    public Mono<ResponseEntity<Map<String, String>>> kakaoLogin(@RequestHeader("Kakao-Access-Token") String kakaoAccessToken) {
        return authService.loginWithKakao(kakaoAccessToken)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@RequestHeader("X-Member-Id") String memberId) {
        return ResponseEntity.ok(authService.refresh(memberId));
    }
}


