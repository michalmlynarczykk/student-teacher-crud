package michalmlynarczyk.studentteachercrud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Entity(name = "teacher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SequenceGenerator(
        name = "default_generator",
        sequenceName = "teacher_sequence",
        allocationSize = 1
)
public class Teacher extends BasePerson {
    @NotBlank(message = "Subject cannot be blank")
    @Size(min = 3, message = "Subject must be longer than 2 characters")
    private String subject;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "student_teacher",
            joinColumns = @JoinColumn(
                    name = "teacher_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "student_id",
                    referencedColumnName = "id"
            )
    )
    @JsonIgnore
    private Set<Student> students;

    public void addStudent(Student student) {
        if (students == null) {
            students = new HashSet<>();
        }
        students.add(student);
        if (student.getTeachers() == null){
            student.setTeachers(new HashSet<>());
        }
        student.getTeachers().add(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.getTeachers().remove(this);
    }
}
