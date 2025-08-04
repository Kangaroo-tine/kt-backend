package Kangcrew.kangaroo_tine.domain.user.api;

import Kangcrew.kangaroo_tine.domain.user.application.UserService;
import Kangcrew.kangaroo_tine.domain.user.dto.request.UserRequestDTO;
import Kangcrew.kangaroo_tine.global.common.response.BaseResponse;
import Kangcrew.kangaroo_tine.global.error.code.status.SuccessStatus;
import Kangcrew.kangaroo_tine.global.security.userDetail.KangarootineUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
