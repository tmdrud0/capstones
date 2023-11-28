package capstone.app.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@ControllerAdvice
public class MethodArgumentNotValidHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(MethodArgumentNotValidException e) {
        String message = Optional.ofNullable(e.getFieldError())
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse("");

        return ResponseEntity
                .status(ErrorCode.ARGUMENT_NOT_VALID.getHttpStatus())
                .body(ErrorResponseEntity.builder()
                        .status(ErrorCode.ARGUMENT_NOT_VALID.getHttpStatus().value())
                        .code(ErrorCode.ARGUMENT_NOT_VALID.name())
                        .message(message)
                        .build()
                );
    }
}
