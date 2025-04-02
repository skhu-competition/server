package server.global.exception;

import lombok.Getter;
import server.global.exception.code.ErrorCode;

@Getter
public class CustomException extends RuntimeException {
  private final ErrorCode errorCode;

  public CustomException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public int getHttpStatus() {
    return errorCode.getHttpStatusCode();
  }

  public String getMessage() {
    return errorCode.getMessage();
  }
}
