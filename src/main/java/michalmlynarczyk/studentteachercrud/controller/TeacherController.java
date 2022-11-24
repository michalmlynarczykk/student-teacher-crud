package michalmlynarczyk.studentteachercrud.controller;

import michalmlynarczyk.studentteachercrud.entity.Teacher;
import michalmlynarczyk.studentteachercrud.service.GenericService;
import michalmlynarczyk.studentteachercrud.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;

@RestController
@RequestMapping("api/v1/school/teachers")
public class TeacherController extends GenericController<Teacher> {
    private final TeacherService teacherService;

    public TeacherController(GenericService<Teacher> service, TeacherService teacherService) {
        super(service);
        this.teacherService = teacherService;
    }

    @GetMapping(params = "student-id")
    public ResponseEntity<Object> getAllTeachersByStudent(@RequestParam("student-id") Long studentId) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("studentId", studentId);
        response.put("teacher list", teacherService.getAllTeachersByStudent(studentId));
        return ResponseEntity.ok(response);
    }

    @PatchMapping(path = "/{id}/student", params = {"student-id", "op"})
    public ResponseEntity<Object> updateStudentList(
            @PathVariable("id") Long teacherId,
            @RequestParam("student-id") Long studentId,
            @RequestParam("op") String op) {
        if (op.equals("add")) {
            teacherService.addStudentToTeacher(teacherId, studentId);
        } else if (op.equals("remove")) {
            teacherService.deleteStudentFromTeacher(teacherId, studentId);
        } else {
            throw new EntityNotFoundException(String.format("Operation parameter: %s not recognized", op));
        }
        return ResponseEntity
                .ok(String.format("Student list for teacher with id: %d updated successfully,operation: %s",
                        teacherId,
                        op));
    }

}
