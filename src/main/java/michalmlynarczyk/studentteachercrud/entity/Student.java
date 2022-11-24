package michalmlynarczyk.studentteachercrud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "student")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@SequenceGenerator(
        name = "default_generator",
        sequenceName = "student_sequence",
        allocationSize = 1
)
public class Student extends BasePerson {
    @NotBlank(message = "Field of study cannot be blank")
    @Size(min = 3, message = "Field of study must be longer than 2 characters")
    private String fieldOfStudy;

    @ManyToMany(
            mappedBy = "students",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<Teacher> teachers;

    public void addTeacher(Teacher teacher) {
        if (teachers == null) {
            teachers = new HashSet<>();
        }
        teachers.add(teacher);
        if (teacher.getStudents() == null){
            teacher.setStudents(new HashSet<>());
        }
        teacher.getStudents().add(this);
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
        teacher.getStudents().remove(this);
    }
}
