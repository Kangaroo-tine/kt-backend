package Kangcrew.kangaroo_tine.member.kakao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class KakaoClient {
    private final WebClient webClient = WebClient.builder().build();
    private final String userInfoUrl;

    public KakaoClient(@Value("${kakao.userinfo-url:https://kapi.kakao.com/v2/user/me}") String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }

    public Mono<Map> getUserInfo(String accessToken) {
        return webClient.post()
                .uri(userInfoUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .headers(h -> h.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(Map.class);
    }
}


