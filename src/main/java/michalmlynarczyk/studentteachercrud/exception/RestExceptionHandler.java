package michalmlynarczyk.studentteachercrud.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Objects;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ExceptionDetails details = new ExceptionDetails(
                new Date(),
                Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(),
                ex.getClass().getSimpleName());

        return new ResponseEntity<>(details, headers, status);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    protected ResponseEntity<Object> handleEntityAlreadyExists(EntityAlreadyExistsException ex) {
        ExceptionDetails details = new ExceptionDetails(
                new Date(),
                ex.getMessage(),
                ex.getClass().getSimpleName());
        return ResponseEntity
                .unprocessableEntity()
                .body(details);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ExceptionDetails details = new ExceptionDetails(
                new Date(),
                ex.getMessage(),
                ex.getClass().getSimpleName());
        return ResponseEntity
                .unprocessableEntity()
                .body(details);
    }

}
