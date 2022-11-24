package michalmlynarczyk.studentteachercrud.repository;

import michalmlynarczyk.studentteachercrud.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends GenericRepository<Student> {
    @Query(
            value = "SELECT s FROM student s JOIN FETCH s.teachers t WHERE t.id =?1")
    List<Student> findAllByTeacherId(Long teacherId);
}
