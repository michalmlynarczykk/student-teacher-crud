package michalmlynarczyk.studentteachercrud.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class ExceptionDetails {
    private Date timestamp;
    private String message;
    private String details;
}
