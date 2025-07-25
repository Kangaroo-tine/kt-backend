package Kangcrew.kangaroo_tine.global.exception;

import Kangcrew.kangaroo_tine.domain.mission.exception.MissionNotFoundException;
import Kangcrew.kangaroo_tine.global.common.response.BaseResponse;
import Kangcrew.kangaroo_tine.global.error.code.ReasonDTO;
import Kangcrew.kangaroo_tine.global.error.code.status.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> handleGeneralException(
            GeneralException e, HttpServletRequest request) {

        BaseResponse<Object> body = BaseResponse.onFailure(e.getCode(), null);
        WebRequest webRequest = new ServletWebRequest(request);

        return super.handleExceptionInternal(
                e, body, new HttpHeaders(),
                e.getCode().getReasonHttpStatus().getHttpStatus(),
                webRequest
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> errors = new LinkedHashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String message = Optional.ofNullable(error.getDefaultMessage()).orElse("잘못된 입력입니다.");
            errors.put(field, message);
        });

        BaseResponse<Object> body = BaseResponse.onFailure(ErrorStatus._BAD_REQUEST, errors);
        return super.handleExceptionInternal(e, body, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException e, HttpServletRequest request) {

        String errorMessage = e.getConstraintViolations().stream()
                .map(violation -> violation.getMessage())
                .findFirst()
                .orElse("잘못된 요청입니다.");

        BaseResponse<Object> body = BaseResponse.onFailure(ErrorStatus._BAD_REQUEST, errorMessage);
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception e, HttpServletRequest request) {
        log.error("Unexpected error occurred: ", e);

        BaseResponse<Object> body =
                BaseResponse.onFailure(ErrorStatus._INTERNAL_SERVER_ERROR, e.getMessage());

        return ResponseEntity
                .status(ErrorStatus._INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(body);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<Object>> handleCustomException(CustomException e) {
        ReasonDTO reason = e.getErrorCode().getReasonHttpStatus();
        BaseResponse<Object> response = BaseResponse.fromReasonDTO(reason, null);
        return ResponseEntity.status(reason.getHttpStatus()).body(response);
    }

    @ExceptionHandler(MissionNotFoundException.class)
    public ResponseEntity<BaseResponse<Object>> handleMissionNotFoundException(MissionNotFoundException e) {
        BaseResponse<Object> response = new BaseResponse<>(false, "MISSION_404", "해당 대상자의 오늘 미션 정보가 존재하지 않습니다.", null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
