package michalmlynarczyk.studentteachercrud.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
public abstract class BasePerson implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_generator")
    private Long id;

    @NotBlank(message = "First name cannot be blank")
    @Size(min = 3, message = "First name must be longer than 2 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 3, message = "Last name must be longer than 2 characters")
    private String lastName;

    /* sticking to task requirements there is field age, in more complex applications it will be better to
       store date of birth and then calculate the age */
    @NotNull(message = "Age cannot be null")
    @Min(value = 18, message = "Age cannot be less than 18")
    private Integer age;

    @NotBlank(message = "Email cannot be blank")
    @Email(
            regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Email not valid"
    )
    @Column(unique = true)
    private String email;
}
