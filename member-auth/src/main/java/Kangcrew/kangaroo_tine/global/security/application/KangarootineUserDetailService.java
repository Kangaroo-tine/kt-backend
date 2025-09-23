package Kangcrew.kangaroo_tine.global.security.application;

import Kangcrew.kangaroo_tine.domain.user.domain.entitiy.User;
import Kangcrew.kangaroo_tine.domain.user.domain.repository.UserRepository;
import Kangcrew.kangaroo_tine.global.security.userDetail.KangarootineUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class KangarootineUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Long id = Long.parseLong(userId);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return new KangarootineUserDetails(user);
    }
}
