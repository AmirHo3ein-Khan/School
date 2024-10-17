package service.impl;

import exception.NotFoundException;
import model.Exam;
import model.Teacher;
import model.dto.TeacherDto;
import repository.ExamRepository;
import repository.TeacherRepository;
import service.TeacherService;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final ExamRepository examRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository, ExamRepository examRepository) {
        this.teacherRepository = teacherRepository;
        this.examRepository = examRepository;
    }

    @Override
    public void creat(Teacher entity) {
        try {
            teacherRepository.creat(entity);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Teacher entity) {
        try {
            teacherRepository.update(entity);
        } catch (SQLException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Optional<TeacherDto> findById(Long id) {
        try {
            Optional<Teacher> teacher = teacherRepository.findById(id);
            if (teacher.isPresent()) {
                Teacher t = teacher.get();
                TeacherDto teacherDto = new TeacherDto();
                teacherDto.setTeacherId(t.getId());
                teacherDto.setCourseTitle(teacherRepository.getCourseTitleThatTeacherTeach(id));
                teacherDto.setFirstName(t.getFirstName());
                teacherDto.setLastName(t.getLastName());
                teacherDto.setNationalCode(t.getNationalCode());
                teacherDto.setDob(t.getDob());
                teacherDto.setEntryDate(t.getEntryDate());
                return Optional.of(teacherDto);
            }
        } catch (SQLException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public void delete(Long id) {
        try {
            teacherRepository.delete(id);
        } catch (SQLException | NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Set<TeacherDto> getAll() {
        Set<TeacherDto> teachersDto = new HashSet<>();
        try {
            Set<Teacher> allTeachers = this.teacherRepository.getAll();
            for (Teacher teacher : allTeachers ) {
                TeacherDto teacherDto = new TeacherDto();
                teacherDto.setTeacherId(teacher.getId());
                teacherDto.setCourseTitle(teacherRepository.getCourseTitleThatTeacherTeach(teacher.getId()));
                teacherDto.setFirstName(teacher.getFirstName());
                teacherDto.setLastName(teacher.getLastName());
                teacherDto.setNationalCode(teacher.getNationalCode());
                teacherDto.setDob(teacher.getDob());
                teacherDto.setEntryDate(teacher.getEntryDate());
                teachersDto.add(teacherDto);
            }
            return teachersDto;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return teachersDto;
    }


    @Override
    public int getCount()  {
        int count = 0;
        try {
            count = this.teacherRepository.getCount();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }
    @Override
    public void creatExam(Exam exam) {
        try {
            this.examRepository.creat(exam);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
