package repository;

import model.Student;
import model.dto.StudentCourseAndGrades;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface StudentRepository extends BaseRepository <Student> {
    void studentGetCourse(Long studentId , Long courseId) throws SQLException;
    Set<StudentCourseAndGrades> seeStudentGrad(Long studentId) throws SQLException;
}
