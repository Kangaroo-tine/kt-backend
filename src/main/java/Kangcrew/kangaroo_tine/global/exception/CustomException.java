package Kangcrew.kangaroo_tine.global.exception;

import Kangcrew.kangaroo_tine.global.error.code.BaseCode;

public class CustomException extends RuntimeException {
    private final BaseCode errorCode;

    public CustomException(BaseCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BaseCode getErrorCode() {
        return errorCode;
    }
}
