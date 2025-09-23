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
}
