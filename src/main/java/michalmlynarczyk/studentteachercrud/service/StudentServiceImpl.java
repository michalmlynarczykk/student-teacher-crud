package michalmlynarczyk.studentteachercrud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import michalmlynarczyk.studentteachercrud.entity.Student;
import michalmlynarczyk.studentteachercrud.entity.Teacher;
import michalmlynarczyk.studentteachercrud.repository.GenericRepository;
import michalmlynarczyk.studentteachercrud.repository.StudentRepository;
import michalmlynarczyk.studentteachercrud.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Validator;
import java.util.List;

@Service
@Slf4j
public class StudentServiceImpl extends GenericService<Student> implements StudentService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public StudentServiceImpl(GenericRepository<Student> repository,
                              ObjectMapper objectMapper,
                              StudentRepository studentRepository,
                              Validator validator,
                              TeacherRepository teacherRepository) {
        super(repository, objectMapper, validator);
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    @Transactional
    public void addTeacherToStudent(Long teacherId, Long studentId) {
        Teacher teacher = getTeacher(teacherId);
        Student student = getStudent(studentId);
        student.addTeacher(teacher);
    }

    @Override
    @Transactional
    public void deleteTeacherFromStudent(Long teacherId, Long studentId) {
        Teacher teacher = getTeacher(teacherId);
        Student student = getStudent(studentId);
        if (!student.getTeachers().contains(teacher)) {
            log.error("Student with id: {} doesn't have teacher with id {}", studentId, teacherId);
            throw new EntityNotFoundException(String.format("Student with id: %d doesn't have teacher with id %d",
                    studentId,
                    teacherId));
        }
        student.removeTeacher(teacher);
    }

    @Override
    public List<Student> getAllStudentsByTeacher(Long teacherId) {
        List<Student> studentsByTeacher = studentRepository.findAllByTeacherId(teacherId);
        if (studentsByTeacher.isEmpty()) {
            log.error("Teacher with id: {} doesn't have any students", teacherId);
            throw new EntityNotFoundException(String.format("Teacher with id: %d doesn't have any students", teacherId));
        }
        return studentsByTeacher;
    }

    private Student getStudent(Long studentId) {
        return studentRepository
                .findById(studentId)
                .orElseThrow(() -> {
                    log.error("Student with id {} doesn't exists", studentId);
                    return new EntityNotFoundException(String.format("Student with id %d doesn't exists", studentId));
                });
    }

    private Teacher getTeacher(Long teacherId) {
        return teacherRepository
                .findById(teacherId)
                .orElseThrow(() -> {
                    log.error("Teacher with id {} doesn't exists", teacherId);
                    return new EntityNotFoundException(String.format("Teacher with id %d doesn't exists", teacherId));
                });
    }
}
