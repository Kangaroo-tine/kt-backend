package Kangcrew.kangaroo_tine.domain.user.application;

import Kangcrew.kangaroo_tine.domain.user.dto.request.UserRequestDTO;

public interface UserService {
    void setUserRole(UserRequestDTO.SetUserRoleDTO request);
}
