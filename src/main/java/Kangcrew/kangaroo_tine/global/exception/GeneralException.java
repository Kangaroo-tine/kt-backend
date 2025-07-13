package Kangcrew.kangaroo_tine.global.exception;

import Kangcrew.kangaroo_tine.global.error.code.BaseErrorCode;
import Kangcrew.kangaroo_tine.global.error.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

  private BaseErrorCode code;

  public ErrorReasonDTO getErrorReasonHttpStatus() {
    return this.code.getReasonHttpStatus();
  }
}

