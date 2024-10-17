package service.impl;

import exception.NotFoundException;
import model.Exam;
import model.dto.ExamDto;
import repository.CourseRepository;
import repository.ExamRepository;
import repository.TeacherRepository;
import service.ExamService;
import util.Printer;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class ExamServiceImpl implements ExamService {
    private final ExamRepository examRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    public ExamServiceImpl(ExamRepository examRepository, TeacherRepository teacherRepository, CourseRepository courseRepository) {
        this.examRepository = examRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void creat(Exam entity) {
        try {
            examRepository.creat(entity);
        } catch (SQLException e) {
            Printer.printError(e.getMessage());
        }
    }

    @Override
    public void update(Exam entity) {
        try {
            examRepository.update(entity);
        } catch (SQLException | NotFoundException e) {
            Printer.printError(e.getMessage());
        }
    }

    @Override
    public Optional<ExamDto> findById(Long id) {
        try {
            Optional<Exam> exam = examRepository.findById(id);
            if (exam.isPresent()) {
                Exam e = exam.get();
                ExamDto examDto = new ExamDto();
                examDto.setExamId(exam.get().getId());
                examDto.setTeacherFullName(teacherRepository.getTeacherFullNameOfTakenExamByExamId(id));
                examDto.setCourseTitle(courseRepository.getCourseTitleOfExamByExamId(id));
                examDto.setExamDate(exam.get().getDate());
                return Optional.of(examDto);
            }
        } catch (SQLException | NotFoundException e) {
            Printer.printError(e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public void delete(Long id) {
        try {
            examRepository.delete(id);
        } catch (SQLException | NotFoundException e) {
            Printer.printError(e.getMessage());
        }
    }

    @Override
    public Set<ExamDto> getAll() {
        Set<ExamDto> examsDto = new HashSet<>();
        try {
            Set<Exam> allExams = this.examRepository.getAll();
            for (Exam exam : allExams) {
                ExamDto examDto = new ExamDto();
                examDto.setExamId(exam.getId());
                examDto.setTeacherFullName(teacherRepository.getTeacherFullNameOfTakenExamByExamId(exam.getId()));
                examDto.setCourseTitle(courseRepository.getCourseTitleOfExamByExamId(exam.getId()));
                examDto.setExamDate(exam.getDate());
                examsDto.add(examDto);
            }
            return examsDto;
        } catch (SQLException e) {
            Printer.printError(e.getMessage());
        }
        return examsDto;
    }


    @Override
    public int getCount() {
        int count = 0;
        try {
            count = this.examRepository.getCount();
        } catch (SQLException e) {
            Printer.printError(e.getMessage());
        }
        return count;
    }

    @Override
    public Set<ExamDto> getAllExamOfStudent(Long userId) {
        Set<ExamDto> examsDto = new HashSet<>();
        try {
            Set<Exam> allExamOfStudent = this.examRepository.getAllExamOfStudent(userId);
            for (Exam exam : allExamOfStudent) {
                ExamDto examDto = new ExamDto();
                examDto.setExamId(exam.getId());
                examDto.setTeacherFullName(teacherRepository.getTeacherFullNameOfTakenExamByExamId(exam.getId()));
                examDto.setCourseTitle(courseRepository.getCourseTitleOfExamByExamId(exam.getId()));
                examDto.setExamDate(exam.getDate());
                examsDto.add(examDto);
            }
        } catch (SQLException e) {
            Printer.printError(e.getMessage());
        }
        return examsDto;
    }
}
