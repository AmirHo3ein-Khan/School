package service;

import model.Course;
import model.dto.CourseDto;
import java.util.Optional;
import java.util.Set;

public interface CourseService extends BaseService <Course>{
    Optional<CourseDto> findById (Long id);
    Set<CourseDto> getAll();
    void studentGetCourse(Long studentId , Long courseId);
    void deleteStudentCourse(Long id);
    Set<String> getAllCourseTitleOfStudent(Long studentId);
}
