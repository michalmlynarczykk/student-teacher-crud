package michalmlynarczyk.studentteachercrud.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDTO {
    private Long id;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 3, message = "First name must be longer than 2 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 3, message = "Last name must be longer than 2 characters")
    private String lastName;

    @NotNull(message = "Age cannot be null")
    @Min(value = 18, message = "Age cannot be less than 18")
    private Integer age;

    @NotBlank(message = "Email cannot be blank")
    @Email(
            regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Email not valid"
    )
    private String email;

    @NotBlank(message = "Subject cannot be blank")
    @Size(min = 3, message = "Subject must be longer than 2 characters")
    private String subject;
}
