package service;

import model.Exam;
import model.Teacher;
import model.dto.TeacherDto;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public interface TeacherService extends BaseService <Teacher>{
    Optional<TeacherDto> findById (Long id);
    Set<TeacherDto> getAll();
    Long getCourseIdThatTeacherTeach(Long id);
    void creatExam (Exam exam);

}
