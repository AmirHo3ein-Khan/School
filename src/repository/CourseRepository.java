package repository;

import model.Course;
import java.sql.SQLException;
import java.util.Set;

public interface CourseRepository extends BaseRepository <Course> {
    String getCourseTitleOfExamByExamId(Long id) throws SQLException;
    void deleteStudentCourse(Long id) throws SQLException;
    Set<String> getAllCourseTitleOfStudent(Long studentId)throws SQLException;
}
