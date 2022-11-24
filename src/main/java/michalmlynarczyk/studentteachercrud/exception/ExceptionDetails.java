package michalmlynarczyk.studentteachercrud.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
public class ExceptionDetails implements Serializable {
    private String timestamp;
    private String message;
    private String details;
}
