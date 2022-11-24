package michalmlynarczyk.studentteachercrud.repository;

import michalmlynarczyk.studentteachercrud.entity.Student;
import michalmlynarczyk.studentteachercrud.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
class TeacherRepositoryTest {
    private final StudentRepository studentRepository;
    private final TeacherRepository underTest;

    @Autowired
    TeacherRepositoryTest(StudentRepository studentRepository, TeacherRepository underTest) {
        this.studentRepository = studentRepository;
        this.underTest = underTest;
    }

    @Test
    void shouldFindOneTeacherByStudentId() {
        //given
        Student student = Student.builder().fieldOfStudy("Math").build();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setAge(20);
        student.setEmail("test@gmail.com");


        Teacher teacher = Teacher.builder().subject("Algebra").build();
        teacher.setFirstName("Mario");
        teacher.setLastName("Doe");
        teacher.setAge(55);
        teacher.setEmail("mariodoe@gmail.com");

        teacher.addStudent(student);
        underTest.save(teacher);
        Student studentFromDb = studentRepository.findByEmail("test@gmail.com").get();

        //when
        List<Teacher> teachersByStudent = underTest.findAllByStudentId(studentFromDb.getId());

        //then
        assertThat(teachersByStudent).isNotEmpty();
        assertThat(teachersByStudent.get(0)).isEqualTo(teacher);
    }

    @Test
    void shouldFindAllTeachersByStudentId() {
        //given
        Student student = Student.builder().fieldOfStudy("Math").build();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setAge(20);
        student.setEmail("test@gmail.com");


        Teacher teacher = Teacher.builder().subject("Algebra").build();
        teacher.setFirstName("Mario");
        teacher.setLastName("Doe");
        teacher.setAge(55);
        teacher.setEmail("mariodoe@gmail.com");

        Teacher secondTeacher = Teacher.builder().subject("Calculus").build();
        secondTeacher.setFirstName("NotMario");
        secondTeacher.setLastName("Doe");
        secondTeacher.setAge(35);
        secondTeacher.setEmail("doesecond@gmail.com");

        student.addTeacher(teacher);
        student.addTeacher(secondTeacher);
        studentRepository.save(student);
        Student studentFromDb = studentRepository.findByEmail("test@gmail.com").get();

        //when
        List<Teacher> teachersByStudent = underTest.findAllByStudentId(studentFromDb.getId());

        //then
        assertThat(teachersByStudent).isNotEmpty();
        assertThat(teachersByStudent.size()).isEqualTo(2);
    }


    @Test
    void shouldNotFindTeacherByStudentIdWhenStudentIdNotValid() {
        //given
        Student student = Student.builder().fieldOfStudy("Math").build();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setAge(20);
        student.setEmail("test@gmail.com");


        Teacher teacher = Teacher.builder().subject("Algebra").build();
        teacher.setFirstName("Mario");
        teacher.setLastName("Doe");
        teacher.setAge(55);
        teacher.setEmail("mariodoe@gmail.com");

        teacher.addStudent(student);
        underTest.save(teacher);

        //when
        List<Teacher> teachersByStudent = underTest.findAllByStudentId(22333L);

        //then
        assertThat(teachersByStudent).isEmpty();
    }

    @Test
    void shouldNotFindTeacherByStudentIdWhenNoStudentsTeachersToStudent() {
        //given
        Student student = Student.builder().fieldOfStudy("Math").build();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setAge(20);
        student.setEmail("test@gmail.com");

        studentRepository.save(student);
        Student studentFromDb = studentRepository.findByEmail("test@gmail.com").get();
        //when
        List<Teacher> teachersByStudent = underTest.findAllByStudentId(studentFromDb.getId());

        //then
        assertThat(teachersByStudent).isEmpty();
    }

}