package michalmlynarczyk.studentteachercrud.repository;

import michalmlynarczyk.studentteachercrud.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase
class GenericRepositoryTest {
    private final GenericRepository<Student> underTest;

    @Autowired
    GenericRepositoryTest(GenericRepository<Student> underTest) {
        this.underTest = underTest;
    }

    @Test
    void shouldFindByEmail() {
        //given
        String email = "test@gmail.com";
        Student student = Student.builder().fieldOfStudy("Math").build();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setAge(20);
        student.setEmail(email);
        underTest.save(student);

        //when
        Optional<Student> studentFromDb = underTest.findByEmail(email);

        //then
        assertThat(studentFromDb).isPresent();
        assertThat(studentFromDb.get()).isEqualTo(student);
    }

    @Test
    void shouldNotFindByEmail() {
        //given
        String email = "test@gmail.com";
        Student student = Student.builder().fieldOfStudy("Math").build();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setAge(20);
        student.setEmail(email);
        underTest.save(student);

        //when
        Optional<Student> studentFromDb = underTest.findByEmail("notvalidemail.gmail.com");

        //then
        assertThat(studentFromDb).isEmpty();
    }

    @Test
    void shouldFindOneStudentByFirstNameAndLastName() {
        //given
        String fName = "John";
        String lName = "Doe";
        Student student = Student.builder().fieldOfStudy("Math").build();
        student.setFirstName(fName);
        student.setLastName(lName);
        student.setAge(20);
        student.setEmail("test@gmail.com");
        underTest.save(student);

        //when
        List<Student> studentsFromDb = underTest.findAllByFirstNameAndLastName(fName,lName);

        //then
        assertThat(studentsFromDb).isNotEmpty();
        assertThat(studentsFromDb.size()).isEqualTo(1);
        assertThat(studentsFromDb.get(0)).isEqualTo(student);
    }

    @Test
    void shouldFindAllStudentsByFirstNameAndLastName() {
        //given
        String fName = "John";
        String lName = "Doe";
        Student student = Student.builder().fieldOfStudy("Math").build();
        student.setFirstName(fName);
        student.setLastName(lName);
        student.setAge(20);
        student.setEmail("test@gmail.com");

        Student student1 = Student.builder().fieldOfStudy("Physics").build();
        student1.setFirstName(fName);
        student1.setLastName(lName);
        student1.setAge(22);
        student1.setEmail("anothertest@gmail.com");

        underTest.save(student);
        underTest.save(student1);

        //when
        List<Student> studentsFromDb = underTest.findAllByFirstNameAndLastName(fName,lName);

        //then
        assertThat(studentsFromDb).isNotEmpty();
        assertThat(studentsFromDb.size()).isEqualTo(2);
    }

    @Test
    void shouldNotFindAllStudentsByFirstNameAndLastName() {
        //given
        Student student = Student.builder().fieldOfStudy("Math").build();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setAge(20);
        student.setEmail("test@gmail.com");
        underTest.save(student);

        //when
        List<Student> studentsFromDb = underTest.findAllByFirstNameAndLastName("NotJohn","NotDoe");

        //then
        assertThat(studentsFromDb).isEmpty();
    }
}