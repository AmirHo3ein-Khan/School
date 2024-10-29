package service.impl;

import exception.NotFoundException;
import exception.RepetitiveUsernameException;
import model.Student;
import model.dto.StudentCourseGrades;
import model.dto.StudentDto;
import repository.StudentRepository;
import service.StudentService;
import util.Printer;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void creat(Student entity) {
        try {
            studentRepository.creat(entity);
        } catch (SQLException | RepetitiveUsernameException e) {
            Printer.printError(e.getMessage());
        }
    }

    @Override
    public void update(Student entity) {
        try {
            studentRepository.update(entity);
        } catch (SQLException | NotFoundException e) {
            Printer.printError(e.getMessage());
        }
    }

    @Override
    public Optional<StudentDto> findById(Long id) {
        try {
            Optional<Student> student = studentRepository.findById(id);
            if (student.isPresent()) {
                StudentDto studentDto = new StudentDto();
                Student s = student.get();
                studentDto.setStudentId(s.getId());
                studentDto.setFirstName(s.getFirstName());
                studentDto.setLastName(s.getLastName());
                studentDto.setNationalCode(s.getNationalCode());
                studentDto.setDob(s.getDob());
                studentDto.setEntryDate(s.getEntryDate());
                studentDto.setGpu(s.getGpu());
                return Optional.of(studentDto);
            }
        } catch (SQLException | NotFoundException e) {
            Printer.printError(e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public void delete(Long id) {
        try {
            studentRepository.delete(id);
        } catch (SQLException | NotFoundException e) {
            Printer.printError(e.getMessage());
        }
    }

    @Override
    public Set<StudentDto> getAll() {
        Set<StudentDto> studentsDto = new HashSet<>();
        try {
            Set<Student> allStudents = this.studentRepository.getAll();
            for (Student student : allStudents) {
                StudentDto studentDto = new StudentDto();
                studentDto.setStudentId(student.getId());
                studentDto.setFirstName(student.getFirstName());
                studentDto.setLastName(student.getLastName());
                studentDto.setNationalCode(student.getNationalCode());
                studentDto.setDob(student.getDob());
                studentDto.setEntryDate(student.getEntryDate());
                studentDto.setGpu(student.getGpu());
                studentsDto.add(studentDto);
            }
            return studentsDto;
        } catch (SQLException e) {
            Printer.printError(e.getMessage());
        }
        return studentsDto;
    }

    @Override
    public int getCount() {
        int count = 0;
        try {
            count = this.studentRepository.getCount();
        } catch (SQLException e) {
            Printer.printError(e.getMessage());
        }
        return count;
    }
    @Override
    public void studentGetCourse(Long studentId, Long courseId) {
        try {
            this.studentRepository.studentGetCourse(studentId , courseId);
        } catch (SQLException e) {
            Printer.printError(e.getMessage());
        }
    }

    @Override
    public Set<StudentCourseGrades> seeStudentGrad(Long studentId) {
        try {
             return studentRepository.seeStudentGrad(studentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
