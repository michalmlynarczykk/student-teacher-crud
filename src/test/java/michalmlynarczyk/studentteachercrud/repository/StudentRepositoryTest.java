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
class StudentRepositoryTest {
    private final StudentRepository underTest;
    private final TeacherRepository teacherRepository;
    @Autowired
    StudentRepositoryTest(StudentRepository underTest, TeacherRepository teacherRepository) {
        this.underTest = underTest;
        this.teacherRepository = teacherRepository;
    }

    @Test
    void shouldFindOneStudentByTeacherId() {
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
        teacher.setEmail("mariodoee@gmail.com");

        student.addTeacher(teacher);
        underTest.save(student);
        Teacher teacherFromDb = teacherRepository.findByEmail("mariodoee@gmail.com").get();

        //when
        List<Student> studentsByTeacher = underTest.findAllByTeacherId(teacherFromDb.getId());

        //then
        assertThat(studentsByTeacher).isNotEmpty();
        assertThat(studentsByTeacher.get(0)).isEqualTo(student);
    }

    @Test
    void shouldFindAllStudentsByTeacherId() {
        //given
        Student student = Student.builder().fieldOfStudy("Math").build();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setAge(20);
        student.setEmail("test@gmail.com");

        Student secondStudent = Student.builder().fieldOfStudy("Physics").build();
        secondStudent.setFirstName("Jon");
        secondStudent.setLastName("Doe");
        secondStudent.setAge(19);
        secondStudent.setEmail("secondtest@gmail.com");

        Teacher teacher = Teacher.builder().subject("Algebra").build();
        teacher.setFirstName("Mario");
        teacher.setLastName("Doe");
        teacher.setAge(55);
        teacher.setEmail("mariodoe@gmail.com");

        teacher.addStudent(student);
        teacher.addStudent(secondStudent);
        teacherRepository.save(teacher);
        Teacher teacherFromDb = teacherRepository.findByEmail("mariodoe@gmail.com").get();

        //when
        List<Student> studentsByTeacher = underTest.findAllByTeacherId(teacherFromDb.getId());

        //then
        assertThat(studentsByTeacher).isNotEmpty();
        assertThat(studentsByTeacher.size()).isEqualTo(2);
    }

    @Test
    void shouldNotFindStudentByTeacherIdWhenTeacherIdNotValid() {
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
        teacher.setEmail("mariodoe1@gmail.com");

        student.addTeacher(teacher);
        underTest.save(student);

        //when
        List<Student> studentsByTeacher = underTest.findAllByTeacherId(32132L);

        //then
        assertThat(studentsByTeacher).isEmpty();
    }

    @Test
    void shouldNotFindStudentByTeacherIdWhenNoStudentsAddedToTeacher() {
        //given
        Teacher teacher = Teacher.builder().subject("Algebra").build();
        teacher.setFirstName("Mario");
        teacher.setLastName("Doe");
        teacher.setAge(55);
        teacher.setEmail("mariodoe4@gmail.com");
        teacherRepository.save(teacher);
        Teacher teacherFromDb = teacherRepository.findByEmail("mariodoe4@gmail.com").get();
        //when
        List<Student> studentsByTeacher = underTest.findAllByTeacherId(teacherFromDb.getId());

        //then
        assertThat(studentsByTeacher).isEmpty();
    }
}