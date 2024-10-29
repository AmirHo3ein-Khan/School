package service;

import model.Student;
import model.dto.StudentCourseGrades;
import model.dto.StudentDto;
import java.util.Optional;
import java.util.Set;

public interface StudentService extends BaseService <Student> {
    Optional<StudentDto> findById (Long id);
    Set<StudentDto> getAll();
    void studentGetCourse(Long studentId , Long courseId);
    Set<StudentCourseGrades> seeStudentGrad(Long studentId);
}
