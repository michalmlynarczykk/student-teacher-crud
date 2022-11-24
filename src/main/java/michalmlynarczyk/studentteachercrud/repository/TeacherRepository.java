package michalmlynarczyk.studentteachercrud.repository;

import michalmlynarczyk.studentteachercrud.entity.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepository extends GenericRepository<Teacher> {
    @Query(
            value = "SELECT t FROM teacher t JOIN FETCH t.students s WHERE s.id =?1")
    List<Teacher> findAllByStudentId(Long teacherId);
}
