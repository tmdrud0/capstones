package capstone.app.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

public class BadCredentialHandler {
    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(BadCredentialsException e) {
        return ErrorResponseEntity.toResponseEntity(ErrorCode.FAIL_LOGIN);
    }
}
