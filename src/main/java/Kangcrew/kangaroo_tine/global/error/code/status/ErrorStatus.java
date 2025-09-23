package Kangcrew.kangaroo_tine.global.error.code.status;

import Kangcrew.kangaroo_tine.global.error.code.BaseErrorCode;
import Kangcrew.kangaroo_tine.global.error.code.ErrorReasonDTO;
import Kangcrew.kangaroo_tine.global.error.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 기본 에러
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON4001", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON4002", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON4003", "금지된 요청입니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER4001", "해당 사용자를 찾을 수 없습니다."),
    SUBJECT_NOT_FOUND(HttpStatus.NOT_FOUND, "SUBJECT4001", "해당 대상자 정보를 찾을 수 없습니다."),
    SUBJECT_ALREADY_CONNECTED(HttpStatus.BAD_REQUEST, "SUBJECT4002", "이미 보호자와 연결된 대상자입니다."),
    INVALID_CONNECT_CODE(HttpStatus.BAD_REQUEST, "SUBJECT4003", "유효하지 않은 연결코드입니다."),
    SUBJECT_CONNECTION_CODE_MISMATCH(HttpStatus.BAD_REQUEST, "SUBJECT4004", "대상자의 인증번호가 등록된 정보와 일치하지 않습니다."),
    SUBJECT_ALREADY_HAS_GUARDIAN(HttpStatus.BAD_REQUEST, "SUBJECT4005", "해당 대상자는 이미 보호자와 연결되어 있습니다."),

    GUARDIAN_NOT_FOUND(HttpStatus.NOT_FOUND, "GUARDIAN4001", "해당 보호자 정보를 찾을 수 없습니다."),


    // 인증 관련 에러
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH4011", "유효하지 않은 RefreshToken입니다.");


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
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .httpStatus(httpStatus)
                .isSuccess(false)
                .code(code)
                .message(message)
                .build();
    }
}
