package capstone.app.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class NotFoundHandler {
    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorResponseEntity> handleCustomException(NoHandlerFoundException e) {
        return ErrorResponseEntity.toResponseEntity(ErrorCode.BAD_REQUEST);
    }
}
