package Kangcrew.kangaroo_tine.global.security.token;

import Kangcrew.kangaroo_tine.global.security.authenticationToken.KangarootineAuthenticationToken;
import org.springframework.security.core.Authentication;

public interface TokenManager {
    KangarootineAuthenticationToken readToken(String token);
    String writeToken(Authentication authentication);
}
