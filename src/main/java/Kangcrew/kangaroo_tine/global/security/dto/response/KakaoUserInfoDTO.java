package Kangcrew.kangaroo_tine.global.security.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDTO {
    private Long id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    public static class KakaoAccount {
        private Profile profile;
        public static class Profile {
            private String nickname;
        }
    }
}
