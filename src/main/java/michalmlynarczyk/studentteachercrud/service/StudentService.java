package michalmlynarczyk.studentteachercrud.service;


import michalmlynarczyk.studentteachercrud.entity.Student;

import java.util.List;

public interface StudentService {
    void addTeacherToStudent(Long teacherId, Long studentId);

    void deleteTeacherFromStudent(Long teacherId, Long studentId);

    List<Student> getAllStudentsByTeacher(Long teacherId);
}
