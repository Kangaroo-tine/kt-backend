package Kangcrew.kangaroo_tine.global.security.authenticationToken;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class KangarootineAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private final Long id;

    //인증 전
    public KangarootineAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
        this.id = null;
    }

    //인증 후
    public KangarootineAuthenticationToken(Object principal, Object credentials,
                                           Collection<? extends GrantedAuthority> authorities, Long id) {
        super(principal, credentials, authorities);
        this.id = id;
    }

    @Override
    public Long getDetails() {
        return id;
    }
}
