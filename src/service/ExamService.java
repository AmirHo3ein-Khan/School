package service;

import model.Exam;
import model.dto.ExamDto;
import java.util.Optional;
import java.util.Set;

public interface ExamService extends BaseService <Exam>{
    Optional<ExamDto> findById (Long id);
    Set<ExamDto> getAll() ;
    Set<ExamDto> getAllExamOfStudent(Long userId);
}
