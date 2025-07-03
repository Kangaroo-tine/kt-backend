package Kangcrew.kangaroo_tine.global.config.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse<T> {
    private boolean isSuccess;
    private String code;
    private String message;
    private T result;
}