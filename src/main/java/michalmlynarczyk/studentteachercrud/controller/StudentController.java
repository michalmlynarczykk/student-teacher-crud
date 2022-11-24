package michalmlynarczyk.studentteachercrud.controller;

import michalmlynarczyk.studentteachercrud.entity.Student;
import michalmlynarczyk.studentteachercrud.service.GenericService;
import michalmlynarczyk.studentteachercrud.service.StudentService;
import michalmlynarczyk.studentteachercrud.service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;

@RestController
@RequestMapping("api/v1/school/students")
public class StudentController extends GenericController<Student> {
    private final StudentServiceImpl studentService;

    @Autowired
    public StudentController(GenericService<Student> service, StudentService studentService) {
        super(service);
        this.studentService = (StudentServiceImpl) studentService;
    }

    @GetMapping(params = "teacher-id")
    public ResponseEntity<Object> getAllStudentsByTeacher(@RequestParam("teacher-id") Long teacherId) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("teacherId", teacherId);
        response.put("studentList", studentService.getAllStudentsByTeacher(teacherId));
        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = "/{id}/teacher", params = {"teacher-id", "op"})
    public ResponseEntity<Object> updateTeachersList(
            @PathVariable("id") Long studentId,
            @RequestParam("teacher-id") Long teacherId,
            @RequestParam("op") String op) {
        if (op.equals("add")) {
            studentService.addTeacherToStudent(teacherId, studentId);
        } else if (op.equals("remove")) {
            studentService.deleteTeacherFromStudent(teacherId, studentId);
        } else {
            throw new EntityNotFoundException(String.format("Operation parameter: %s not recognized", op));
        }
        return ResponseEntity
                .ok(String.format("Teacher list for student with id: %d updated successfully,operation: %s",
                        studentId,
                        op));
    }
}
