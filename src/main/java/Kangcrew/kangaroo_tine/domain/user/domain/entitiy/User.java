package Kangcrew.kangaroo_tine.domain.user.domain.entitiy;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long kakaoId;

    private String name;

    private String email;

    private String profileImg;

    public void updateProfile(String name, String email, String profileImg) {
        this.name = name;
        this.email = email;
        this.profileImg = profileImg;
    }
}
