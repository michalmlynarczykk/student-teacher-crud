package michalmlynarczyk.studentteachercrud.student;


import java.util.List;

public interface StudentService {
    Student saveStudent(Student student);

    void deleteStudent(Long studentId);

    void updateStudent(Student student, Long studentId);

    void addTeacherToStudent(Long teacherId, Long studentId);

    void deleteTeacherFromStudent(Long teacherId, Long studentId);

    List<Student> getAllStudents(); //TODO pagination and sorting

    List<Student> getAllStudentsByTeacher(Long teacherId);

    Student getStudentByFirstAndLastName(String firstName, String lastName);
}
