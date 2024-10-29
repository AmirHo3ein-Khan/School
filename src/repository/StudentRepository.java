package repository;

import model.Student;
import model.dto.StudentCourseGrades;

import java.sql.SQLException;
import java.util.Set;

public interface StudentRepository extends BaseRepository <Student> {
    void studentGetCourse(Long studentId , Long courseId) throws SQLException;
    Set<StudentCourseGrades> seeStudentGrad(Long studentId) throws SQLException;
}
