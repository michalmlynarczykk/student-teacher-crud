package michalmlynarczyk.studentteachercrud.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByEmail(String email);

    List<Student> findAllByFirstNameAndLastName(String firstName, String LastName);

    @Query(
            value = "SELECT s FROM student s JOIN FETCH s.teachers t WHERE t.id =?1")
    List<Student> findAllByTeacherId(Long teacherId);
}
