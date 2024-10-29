package service.impl;

import exception.NotFoundException;
import model.Course;
import model.dto.CourseDto;
import repository.CourseRepository;
import service.CourseService;
import util.Printer;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void creat(Course entity) {
        try {
            courseRepository.creat(entity);
        } catch (SQLException e) {
            Printer.printError(e.getMessage());
        }
    }

    @Override
    public void update(Course entity) {
        try {
            courseRepository.update(entity);
        } catch (SQLException | NotFoundException e) {
            Printer.printError(e.getMessage());
        }
    }

    @Override
    public Optional<CourseDto> findById(Long id) {
        try {
            Optional<Course> course = courseRepository.findById(id);
            if (course.isPresent()) {
                Course c = course.get();
                CourseDto courseDto = new CourseDto();
                courseDto.setCourseId(c.getId());
                courseDto.setTitle(c.getTitle());
                courseDto.setUnit(c.getUnite());
                return Optional.of(courseDto);
            }
        } catch (SQLException | NotFoundException e) {
            Printer.printError(e.getMessage());
        }
        return Optional.empty();
    }


    @Override
    public void delete(Long id) {
        try {
            courseRepository.delete(id);
        } catch (SQLException | NotFoundException e) {
            Printer.printError(e.getMessage());
        }
    }

    @Override
    public Set<CourseDto> getAll() {
        Set<CourseDto> coursesDto = new HashSet<>();
        try {
            Set<Course> allCourses = this.courseRepository.getAll();
            for (Course course : allCourses) {
                CourseDto courseDto = new CourseDto();
                courseDto.setCourseId(course.getId());
                courseDto.setTitle(course.getTitle());
                courseDto.setUnit(course.getUnite());
                coursesDto.add(courseDto);
            }
            return coursesDto;
        } catch (SQLException e) {
            Printer.printError(e.getMessage());
        }
        return coursesDto;
    }

    @Override
    public int getCount() {
        int count = 0;
        try {
            count = this.courseRepository.getCount();
        } catch (SQLException e) {
            Printer.printError(e.getMessage());
        }
        return count;
    }

    @Override
    public void deleteStudentCourse(Long id) {
        try {
            this.courseRepository.deleteStudentCourse(id);
        } catch (SQLException e) {
            Printer.printError(e.getMessage());
        }
    }

    @Override
    public Set<String> getAllCourseTitleOfStudent(Long studentId) {
        Set<String> allCourseTitleOfStudent = new HashSet<>();
        try {
            allCourseTitleOfStudent = this.courseRepository.getAllCourseTitleOfStudent(studentId);
        } catch (SQLException  e) {
            Printer.printError(e.getMessage());
        }
        return allCourseTitleOfStudent;
    }

}
