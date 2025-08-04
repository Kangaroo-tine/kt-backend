package Kangcrew.kangaroo_tine.global.security.application;

import Kangcrew.kangaroo_tine.domain.user.domain.UserRole;
import Kangcrew.kangaroo_tine.domain.user.domain.entitiy.User;
import Kangcrew.kangaroo_tine.domain.user.domain.repository.UserRepository;
import Kangcrew.kangaroo_tine.global.error.code.status.ErrorStatus;
import Kangcrew.kangaroo_tine.global.exception.GeneralException;
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

        if (kakaoAccessToken.startsWith("Bearer ")) {
            kakaoAccessToken = kakaoAccessToken.substring(7);
        }

        KakaoUserInfoDTO kakaoUser = kakaoApiClient.getUserInfo(kakaoAccessToken);
        Long kakaoId = kakaoUser.getId();
        String nickname = kakaoUser.getNickname();
        String email = kakaoUser.getEmail();
        String profileImageUrl = kakaoUser.getProfileImageUrl();

        User user = userRepository.findByKakaoId(kakaoId)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .kakaoId(kakaoId)
                            .name(nickname)
                            .email(email)
                            .profileImg(profileImageUrl)
                            .role(null)
                            .build();
                    return userRepository.save(newUser);
                });

        if (user.getName() == null || user.getProfileImg() == null) {
            user.updateProfile(nickname, email, profileImageUrl);
            userRepository.save(user);
        }

        KangarootineAuthenticationToken token =
                new KangarootineAuthenticationToken(user.getKakaoId().toString(), null);

        Authentication authentication = authenticationManager.authenticate(token);

        String accessToken = tokenManager.writeToken(authentication);
        String refreshToken = tokenManager.writeRefreshToken(authentication);

        return new AuthResponseDTO(
                accessToken,
                refreshToken,
                "86400000",
                "604800000"
        );
    }


    @Override
    public AuthResponseDTO refresh(String refreshToken) {

        if (refreshToken.startsWith("Bearer ")) {
            refreshToken = refreshToken.substring(7);
        }

        if (!tokenManager.isValidToken(refreshToken)) {
            throw new GeneralException(ErrorStatus.INVALID_REFRESH_TOKEN);
        }

        Authentication authentication = tokenManager.getAuthentication(refreshToken);

        String newAccessToken = tokenManager.writeToken(authentication);
        String newRefreshToken = tokenManager.writeRefreshToken(authentication);

        return new AuthResponseDTO(
                newAccessToken,
                newRefreshToken,
                "86400000",
                "604800000"
        );
    }

}
