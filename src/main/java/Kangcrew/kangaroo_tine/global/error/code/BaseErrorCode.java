package Kangcrew.kangaroo_tine.global.error.code;

public interface BaseErrorCode {
    String getCode();
    String getMessage();
    ErrorReasonDTO getReasonHttpStatus();
}
