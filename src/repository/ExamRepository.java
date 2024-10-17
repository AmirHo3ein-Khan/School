package repository;

import model.Exam;
import java.sql.SQLException;
import java.util.Set;

public interface ExamRepository extends BaseRepository <Exam> {
    Set<Exam> getAllExamOfStudent(Long userId) throws SQLException;
}
