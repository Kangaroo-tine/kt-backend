package Kangcrew.kangaroo_tine.global.error.code.status;

import Kangcrew.kangaroo_tine.global.error.code.BaseCode;
import Kangcrew.kangaroo_tine.global.error.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
    //Common
    OK(HttpStatus.OK, "COMMON_200", "성공입니다."),

    // 로그인
    LOGIN_SUCCESS(HttpStatus.OK, "LOGIN_200", "로그인에 성공했습니다."),
    REFRESH_SUCCESS(HttpStatus.OK, "REFRESH_200", "토큰 재발급에 성공했습니다."),

    //User
    USER_ROLE_UPDATED(HttpStatus.OK, "USER_2001", "유저 역할이 설정되었습니다."),
    GUARDIAN_PHONE_SAVED(HttpStatus.OK,"USER_2002", "보호자 전화번호가 저장되었습니다."),
    CONNECT_CODE_CREATED(HttpStatus.OK, "USER_2003", "연결 코드가 생성되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .httpStatus(httpStatus)
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }
}
