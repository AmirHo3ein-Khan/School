package service;

import model.Exam;
import model.Teacher;
import model.dto.TeacherDto;
import java.util.Optional;
import java.util.Set;

public interface TeacherService extends BaseService <Teacher>{
    Optional<TeacherDto> findById (Long id);
    Set<TeacherDto> getAll();
    void creatExam (Exam exam);

}
