package Kangcrew.kangaroo_tine.domain.user.api;

import Kangcrew.kangaroo_tine.domain.user.application.UserService;
import Kangcrew.kangaroo_tine.domain.user.dto.request.UserRequestDTO;
import Kangcrew.kangaroo_tine.domain.user.dto.response.UserResponseDTO;
import Kangcrew.kangaroo_tine.global.common.response.BaseResponse;
import Kangcrew.kangaroo_tine.global.error.code.status.SuccessStatus;
import Kangcrew.kangaroo_tine.global.security.userDetail.KangarootineUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "사용자 API", description = "User 관련 API")
public class UserContorller {

    private final UserService userService;

    @PatchMapping("/role")
    @Operation(summary = "사용자 역할 설정", description = "회원 가입 후 사용자 역할(보호자/대상자)을 설정합니다.")
    public BaseResponse<Void> setUserRole(
            @RequestBody @Valid UserRequestDTO.SetUserRoleDTO request,
            @AuthenticationPrincipal KangarootineUserDetails userDetails) {

        Long userId = userDetails.getDatabaseId();
        userService.setUserRole(userId, request);
        return BaseResponse.onSuccess(SuccessStatus.USER_ROLE_UPDATED, null);
    }

    @PostMapping("/guardian-phone")
    @Operation(summary = "보호자 전화번호 저장", description = "대상자가 보호자 전화번호를 저장합니다.")
    public BaseResponse<Void> saveGuardianPhone(
            @RequestBody @Valid UserRequestDTO.SaveGuardianPhoneDTO request,
            @AuthenticationPrincipal KangarootineUserDetails userDetails) {

        Long userId = userDetails.getDatabaseId();
        userService.saveGuardianPhone(userId, request);
        return BaseResponse.onSuccess(SuccessStatus.GUARDIAN_PHONE_SAVED, null);
    }

    @PostMapping("/connect-code")
    @Operation(summary = "연결코드 생성", description = "대상자의 연결코드를 새로 생성합니다.")
    public BaseResponse<UserResponseDTO.ConnectCodeDTO> createConnectCode(
            @AuthenticationPrincipal KangarootineUserDetails userDetails) {

        Long userId = userDetails.getDatabaseId();
        UserResponseDTO.ConnectCodeDTO connectCodeDTO = userService.generateConnectCode(userId);

        return BaseResponse.onSuccess(SuccessStatus.CONNECT_CODE_CREATED, connectCodeDTO);
    }

}
