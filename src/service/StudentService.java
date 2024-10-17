package service;

import model.Student;
import model.dto.StudentDto;
import java.util.Optional;
import java.util.Set;

public interface StudentService extends BaseService <Student> {
    Optional<StudentDto> findById (Long id);
    Set<StudentDto> getAll();
}
