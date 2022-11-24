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
public class TeacherServiceImpl extends GenericService<Teacher> implements TeacherService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public TeacherServiceImpl(GenericRepository<Teacher> repository,
                              TeacherRepository teacherRepository,
                              ObjectMapper objectMapper,
                              Validator validator,
                              StudentRepository studentRepository) {
        super(repository, objectMapper, validator);
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public void addStudentToTeacher(Long teacherId, Long studentId) {
        Teacher teacher = getTeacher(teacherId);
        Student student = getStudent(studentId);
        teacher.addStudent(student);
    }

    @Override
    @Transactional
    public void deleteStudentFromTeacher(Long teacherId, Long studentId) {
        Teacher teacher = getTeacher(teacherId);
        Student student = getStudent(studentId);
        if (!teacher.getStudents().contains(student)) {
            log.error("Teacher with id: {} doesn't have student with id {}", teacherId, studentId);
            throw new EntityNotFoundException(String.format("Teacher with id: %d doesn't have student with id %d",
                    teacherId,
                    studentId));
        }
        teacher.removeStudent(student);
    }

    @Override
    public List<Teacher> getAllTeachersByStudent(Long studentId) {
        List<Teacher> teachersByStudent = teacherRepository.findAllByStudentId(studentId);
        if (teachersByStudent.isEmpty()) {
            log.error("Student with id: {} doesn't have any teachers", studentId);
            throw new EntityNotFoundException(String.format("Student with id: %d doesn't have any teachers", studentId));
        }
        return teachersByStudent;
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
