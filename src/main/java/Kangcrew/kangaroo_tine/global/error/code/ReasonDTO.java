package Kangcrew.kangaroo_tine.global.error.code;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

//에러 정보를 한 번에 전달할 수 있음
@Getter
@Builder
public class ReasonDTO {

    private final HttpStatus httpStatus;
    private final boolean isSuccess;
    private final String code;
    private final String message;
}
