package michalmlynarczyk.studentteachercrud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import michalmlynarczyk.studentteachercrud.entity.Student;
import michalmlynarczyk.studentteachercrud.repository.GenericRepository;
import michalmlynarczyk.studentteachercrud.repository.StudentRepository;
import michalmlynarczyk.studentteachercrud.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    private StudentServiceImpl underTest;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private Validator validator;
    @Mock
    private ObjectMapper mapper;
    @Mock
    private GenericRepository<Student> repository;

    @BeforeEach
    void setUp() {
        underTest = new StudentServiceImpl(
                repository,
                mapper,
                studentRepository,
                validator,
                teacherRepository);
    }

    @Test
    void shouldGetById() {
        //given
        Student student = Student.builder().fieldOfStudy("Math").build();
        student.setId(1L);
        student.setAge(22);
        student.setEmail("student@gmail.com");
        student.setFirstName("John");
        student.setLastName("Doe");

        given(repository.findById(anyLong())).willReturn(Optional.of(student));

        //when
        Student responseStudent = underTest.getById(anyLong());

        //then
        assertThat(student).isEqualTo(responseStudent);
    }

    @Test
    void shouldNotGetById() {
        //given
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        Long personId = 2L;
        //when
        //then
        assertThatThrownBy(() -> underTest.getById(personId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(String.format("Person with id %d doesn't exists", personId));
    }

    @Test
    void shouldGetAll() {
        //given
        int pageIndex = 1;
        int size = 2;
        String sort = "email";
        Student student = Student.builder().fieldOfStudy("Math").build();
        student.setId(1L);
        student.setAge(22);
        student.setEmail("student2@gmail.com");
        student.setFirstName("John");
        student.setLastName("Doe");
        Page<Student> page = new PageImpl<>(List.of(student));
        Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(sort));

        given(repository.findAll(pageable)).willReturn(page);

        //when
        Page<Student> responsePage = underTest.getAll(pageIndex, size, sort);

        //then
        assertThat(page).isEqualTo(responsePage);
    }

    @Test
    void shouldThrowWhenPageParameterNotValid() {
        //given
        int pageIndex = 1;
        int size = 2;
        String sort = "email";
        Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(sort));

        given(repository.findAll(pageable)).willThrow(IllegalStateException.class);

        //when
        //then
        assertThatThrownBy(() -> underTest.getAll(pageIndex, size, sort))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldThrowWhenPageIsEmpty() {
        //given
        int pageIndex = 1;
        int size = 2;
        String sort = "email";
        Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(sort));

        given(repository.findAll(pageable)).willReturn(Page.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.getAll(pageIndex, size, sort))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("No persons available for this request");
    }

    @Test
    void shouldGetAllByFirstAndLastName() {
        //given
        String fName = "Jane";
        String lName = "Doe";

        Student student = Student.builder().fieldOfStudy("Math").build();
        student.setId(1L);
        student.setAge(22);
        student.setEmail("janedoe@gmail.com");
        student.setFirstName(fName);
        student.setLastName(lName);
        List<Student> studentList = List.of(student);

        given(repository.findAllByFirstNameAndLastName(fName, lName))
                .willReturn(studentList);

        //when
        List<Student> responseList = underTest.getAllByFirstAndLastName(fName, lName);

        //then
        assertThat(studentList).isNotEmpty();
        assertThat(studentList).isEqualTo(responseList);
    }

    @Test
    void shouldThrowWhenFirstAndLastNameNotValid() {
        //given
        String fName = "Jane";
        String lName = "Doe";

        given(repository.findAllByFirstNameAndLastName(fName, lName))
                .willReturn(new ArrayList<>());

        //when
        //then
        assertThatThrownBy(() -> underTest.getAllByFirstAndLastName(fName,lName))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(String.format("No persons with name %s %s", fName, lName));
    }

    //TODO write these unit tests
    @Disabled
    @Test
    void save() {
    }

    @Disabled
    @Test
    void delete() {
    }

    @Disabled
    @Test
    void update() {
    }

    @Disabled
    @Test
    void addTeacherToStudent() {
    }

    @Disabled
    @Test
    void deleteTeacherFromStudent() {
    }

    @Disabled
    @Test
    void getAllStudentsByTeacher() {
    }
}