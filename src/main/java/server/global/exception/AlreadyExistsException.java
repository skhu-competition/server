package server.global.exception;

import server.global.exception.code.ErrorCode;

public class AlreadyExistsException extends CustomException{
  public AlreadyExistsException(ErrorCode errorCode, String message) {
    super(errorCode, message);
  }
}
