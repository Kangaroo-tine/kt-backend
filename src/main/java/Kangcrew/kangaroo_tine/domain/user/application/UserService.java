package Kangcrew.kangaroo_tine.domain.user.application;

import Kangcrew.kangaroo_tine.domain.user.dto.request.UserRequestDTO;
import Kangcrew.kangaroo_tine.domain.user.dto.response.UserResponseDTO;

public interface UserService {
    void setUserRole(Long userId,UserRequestDTO.SetUserRoleDTO request);
    void saveGuardianPhone(Long userId, UserRequestDTO.SaveGuardianPhoneDTO request);
    UserResponseDTO.ConnectCodeDTO generateConnectCode(Long userId);
    UserResponseDTO.SubjectRegisterResultDTO registerSubject(Long guardianUserId, UserRequestDTO.SubjectRegisterDTO request);
}
