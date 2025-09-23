package Kangcrew.kangaroo_tine.global.security.provider;

import Kangcrew.kangaroo_tine.domain.user.domain.entitiy.User;
import Kangcrew.kangaroo_tine.domain.user.domain.repository.UserRepository;
import Kangcrew.kangaroo_tine.global.error.code.status.ErrorStatus;
import Kangcrew.kangaroo_tine.global.exception.GeneralException;
import Kangcrew.kangaroo_tine.global.security.authenticationToken.KangarootineAuthenticationToken;
import Kangcrew.kangaroo_tine.global.security.userDetail.KangarootineUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KangarootineAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Long kakaoId = Long.parseLong(authentication.getPrincipal().toString());
        User user = userRepository.findByKakaoId(kakaoId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        KangarootineUserDetails userDetails = new KangarootineUserDetails(user);

        return new KangarootineAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities(),
                user.getId()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return KangarootineAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
