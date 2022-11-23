package michalmlynarczyk.studentteachercrud.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import michalmlynarczyk.studentteachercrud.teacher.Teacher;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(generator = "student_sequence")
    private Long id;
    private String firstName;
    private String lastName;
    /* sticking to task requirements there is field age, in more complex applications it will be better to
       store date of birth and then calculate the age */
    private Integer age;
    @Column(unique = true)
    private String email;
    private String fieldOfStudy;

    @ManyToMany(
            mappedBy = "students",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Teacher> teachers;

    public void addTeacher(Teacher teacher) {
        if (teachers == null) {
            teachers = new HashSet<>();
        }
        teachers.add(teacher);
        teacher.getStudents().add(this);
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
        teacher.getStudents().remove(this);
    }
}
