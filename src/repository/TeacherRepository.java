package repository;
import model.Teacher;
import java.sql.SQLException;

public interface TeacherRepository extends BaseRepository <Teacher> {
    String getCourseTitleThatTeacherTeach(Long id) throws SQLException;
    Long getCourseIdThatTeacherTeach(Long id) throws SQLException;
    String getTeacherFullNameOfTakenExamByExamId(Long id) throws SQLException;

}
