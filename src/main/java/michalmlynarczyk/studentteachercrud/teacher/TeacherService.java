package michalmlynarczyk.studentteachercrud.teacher;

import java.util.List;

public interface TeacherService {
    Teacher saveTeacher(Teacher teacher);

    void deleteTeacher(Long teacherId);

    void updateStudent(Teacher teacher, Long teacherId);

    void addStudentToTeacher(Long teacherId, Long studentId);

    void deleteStudentFromTeacher(Long teacherId, Long studentId);

    List<Teacher> getAllTeachers(); //TODO pagination and sorting

    List<Teacher> getAllTeachersByStudent(Long teacherId);

    Teacher getTeacherByFirstAndLastName(String firstName, String lastName);
}
