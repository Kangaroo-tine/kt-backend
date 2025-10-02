package Kangcrew.kangaroo_tine.member.service;

import Kangcrew.kangaroo_tine.member.domain.Member;
import Kangcrew.kangaroo_tine.member.kakao.KakaoClient;
import Kangcrew.kangaroo_tine.member.repository.MemberRepository;
import Kangcrew.kangaroo_tine.member.security.JwtProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class AuthService {
    private static final String PROVIDER_KAKAO = "KAKAO";

    private final KakaoClient kakaoClient;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public AuthService(KakaoClient kakaoClient, MemberRepository memberRepository, JwtProvider jwtProvider) {
        this.kakaoClient = kakaoClient;
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    @Transactional
    public Mono<Map<String, String>> loginWithKakao(String kakaoAccessToken) {
        return kakaoClient.getUserInfo(kakaoAccessToken)
                .map(this::extractKakaoId)
                .map(kakaoId -> {
                    Member member = memberRepository.findByProviderAndOauthId(PROVIDER_KAKAO, kakaoId)
                            .orElseGet(() -> createMember(kakaoId));
                    String accessToken = jwtProvider.createAccessToken(member.getId().toString());
                    String refreshToken = jwtProvider.createRefreshToken(member.getId().toString());
                    return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
                });
    }

    public Map<String, String> refresh(String memberId) {
        String newAccess = jwtProvider.createAccessToken(memberId);
        return Map.of("accessToken", newAccess);
    }

    private Member createMember(String kakaoId) {
        Member m = new Member();
        m.setProvider(PROVIDER_KAKAO);
        m.setOauthId(kakaoId);
        m.setNickname("kakao_" + kakaoId);
        return memberRepository.save(m);
    }

    private String extractKakaoId(Map userInfo) {
        Object id = userInfo.get("id");
        return String.valueOf(id);
    }
}


