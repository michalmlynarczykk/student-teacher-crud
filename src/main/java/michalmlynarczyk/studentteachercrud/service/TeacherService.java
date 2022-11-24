package michalmlynarczyk.studentteachercrud.service;

import michalmlynarczyk.studentteachercrud.entity.Teacher;

import java.util.List;

public interface TeacherService {
    void addStudentToTeacher(Long teacherId, Long studentId);

    void deleteStudentFromTeacher(Long teacherId, Long studentId);

    List<Teacher> getAllTeachersByStudent(Long studentId);
}
