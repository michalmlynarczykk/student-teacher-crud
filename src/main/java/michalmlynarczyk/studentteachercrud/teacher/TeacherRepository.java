package michalmlynarczyk.studentteachercrud.teacher;

import michalmlynarczyk.studentteachercrud.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEmail(String email);

    List<Teacher> findAllByFirstNameAndLastName(String firstName, String LastName);

    @Query(
            value = "SELECT t FROM teacher t JOIN FETCH t.students s WHERE s.id =?1")
    List<Student> findAllByStudentId(Long teacherId);
}
