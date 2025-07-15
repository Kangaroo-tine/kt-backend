package Kangcrew.kangaroo_tine.global.security.userDetail;

import org.springframework.security.core.GrantedAuthority;

public enum KangarootineAuthority implements GrantedAuthority {

    SUBJECT,
    GUARDIAN;

    @Override
    public String getAuthority() {
        return name(); // "SUBJECT", "GUARDIAN"
    }
}
