package michalmlynarczyk.studentteachercrud.exception;

public class EntityNotUpdatableException extends RuntimeException {
    public EntityNotUpdatableException(String message) {
        super(message);
    }

    public EntityNotUpdatableException(Throwable cause) {
        super(cause);
    }
}
