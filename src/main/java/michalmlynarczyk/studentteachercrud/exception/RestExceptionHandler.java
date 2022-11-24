package michalmlynarczyk.studentteachercrud.exception;

import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatus status,
            @NonNull WebRequest request) {
        ExceptionDetails details = new ExceptionDetails(
                LocalDateTime.now(Clock.systemDefaultZone()).toString(),
                Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(),
                ex.getClass().getSimpleName());

        return new ResponseEntity<>(details, headers, status);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    protected ResponseEntity<Object> handleEntityAlreadyExists(EntityAlreadyExistsException ex) {
        ExceptionDetails details = new ExceptionDetails(
                LocalDateTime.now(Clock.systemDefaultZone()).toString(),
                ex.getMessage(),
                ex.getClass().getSimpleName());
        return ResponseEntity
                .unprocessableEntity()
                .body(details);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        ExceptionDetails details = new ExceptionDetails(
                LocalDateTime.now(Clock.systemDefaultZone()).toString(),
                ex.getMessage(),
                ex.getClass().getSimpleName());
        return ResponseEntity
                .unprocessableEntity()
                .body(details);
    }

    @ExceptionHandler(EntityNotUpdatableException.class)
    protected ResponseEntity<Object> handleEntityNotUpdatable(EntityNotUpdatableException ex) {
        ExceptionDetails details = new ExceptionDetails(
                LocalDateTime.now(Clock.systemDefaultZone()).toString(),
                ex.getCause().getMessage(),
                ex.getClass().getSimpleName());
        return ResponseEntity
                .unprocessableEntity()
                .body(details);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        ExceptionDetails details = new ExceptionDetails(
                LocalDateTime.now(Clock.systemDefaultZone()).toString(),
                ex.getCause().getMessage(),
                ex.getClass().getSimpleName());
        return ResponseEntity
                .unprocessableEntity()
                .body(details);
    }
}
