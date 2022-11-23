package michalmlynarczyk.studentteachercrud.teacher;

import lombok.*;
import michalmlynarczyk.studentteachercrud.student.Student;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity(name = "teacher")
@Data
@EqualsAndHashCode(exclude = "students")
@ToString(exclude = "students")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {
    @SequenceGenerator(
            name = "teacher_sequence",
            sequenceName = "teacher_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(generator = "teacher_sequence")
    private Long id;
    private String firstName;
    private String lastName;
    /* sticking to the task requirements there is field age, in more complex applications it will be better to
       store date of birth and then calculate the age */
    private Integer age;
    @Column(unique = true)
    private String email;
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
    private Set<Student> students;

    public void addStudent(Student student) {
        if (students == null) {
            students = new HashSet<>();
        }
        students.add(student);
        student.getTeachers().add(this);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.getTeachers().remove(this);
    }
}
