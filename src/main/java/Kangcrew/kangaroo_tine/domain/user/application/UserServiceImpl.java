package Kangcrew.kangaroo_tine.domain.user.application;

import Kangcrew.kangaroo_tine.domain.user.domain.entitiy.User;
import Kangcrew.kangaroo_tine.domain.user.domain.repository.UserRepository;
import Kangcrew.kangaroo_tine.domain.user.dto.request.UserRequestDTO;
import Kangcrew.kangaroo_tine.global.error.code.status.ErrorStatus;
import Kangcrew.kangaroo_tine.global.exception.GeneralException;
import Kangcrew.kangaroo_tine.global.security.application.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void setUserRole(UserRequestDTO.SetUserRoleDTO request) {
        User user = userRepository.findById(SecurityUtil.getCurrentUserId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        user.assignRole(request.getRole());
        userRepository.save(user);
    }
}
