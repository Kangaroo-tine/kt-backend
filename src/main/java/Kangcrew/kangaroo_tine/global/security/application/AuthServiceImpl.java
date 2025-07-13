package Kangcrew.kangaroo_tine.global.security.application;

import Kangcrew.kangaroo_tine.domain.user.domain.UserRole;
import Kangcrew.kangaroo_tine.domain.user.domain.entitiy.User;
import Kangcrew.kangaroo_tine.domain.user.domain.repository.UserRepository;
import Kangcrew.kangaroo_tine.global.security.authenticationToken.KangarootineAuthenticationToken;
import Kangcrew.kangaroo_tine.global.security.dto.response.AuthResponseDTO;
import Kangcrew.kangaroo_tine.global.security.dto.response.KakaoUserInfoDTO;
import Kangcrew.kangaroo_tine.global.security.token.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final KakaoApiClient kakaoApiClient;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenManager tokenManager;

    @Override
    public AuthResponseDTO login(String kakaoAccessToken) {
        System.out.println(">> kakaoAccessToken = " + kakaoAccessToken);
        // 1. Bearer 제거
        if (kakaoAccessToken.startsWith("Bearer ")) {
            kakaoAccessToken = kakaoAccessToken.substring(7);
        }

        // 2. 카카오 사용자 정보 요청
        KakaoUserInfoDTO kakaoUser = kakaoApiClient.getUserInfo(kakaoAccessToken);
        Long kakaoId = kakaoUser.getId();

        // 3. DB에서 사용자 조회 (없으면 생성)
        User user = userRepository.findByKakaoId(kakaoId)
                .orElseGet(() -> {
                    User newUser = new User(null, kakaoId, null); // 여기서 다시 valueOf 하지 않음
                    return userRepository.save(newUser);
                });

        // 4. Authentication 생성
        KangarootineAuthenticationToken token =
                new KangarootineAuthenticationToken(user.getKakaoId().toString(), null);

        Authentication authentication = authenticationManager.authenticate(token);

        // 5. JWT 생성
        String accessToken = tokenManager.writeToken(authentication);

        return new AuthResponseDTO(
                accessToken,
                null,
                "86400000",
                null
        );
    }


    @Override
    public AuthResponseDTO refresh(String refreshToken) {
        return null;
    }
}
