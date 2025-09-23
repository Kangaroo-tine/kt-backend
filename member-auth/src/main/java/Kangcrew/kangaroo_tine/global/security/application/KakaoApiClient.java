package Kangcrew.kangaroo_tine.global.security.application;

import Kangcrew.kangaroo_tine.global.security.dto.response.KakaoUserInfoDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoApiClient {

    private final WebClient webClient;

    public KakaoApiClient(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://kapi.kakao.com").build();
    }

    public KakaoUserInfoDTO getUserInfo(String accessToken) {
        return webClient.get()
                .uri("/v2/user/me")
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUserInfoDTO.class)
                .block();
    }
}
