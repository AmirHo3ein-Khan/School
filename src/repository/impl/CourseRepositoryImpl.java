package repository.impl;
import database.Database;
import exception.NotFoundException;
import model.Course;
import repository.CourseRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static util.Color.RED;
import static util.Color.RESET;


public class CourseRepositoryImpl implements CourseRepository {
    private final Database database = new Database();

    @Override
    public void creat(Course entity) throws SQLException {
        PreparedStatement pr = this.database.getPreparedStatement(Constant.CREATE_COURSE);
        pr.setInt(1, entity.getUnite());
        pr.setString(2, entity.getTitle());
        pr.executeUpdate();
    }

    @Override
    public void update(Course entity) throws SQLException, NotFoundException {
        Optional<Course> course = findById(entity.getId());
        if (course.isPresent()) {
            PreparedStatement pr = database.getPreparedStatement(Constant.UPDATE_COURSE);
            pr.setInt(1, entity.getUnite());
            pr.setString(2, entity.getTitle());
            pr.setLong(3, entity.getId());
            pr.executeUpdate();
            pr.close();
        } else {
            throw new NotFoundException(RED+"Course with ID " + entity.getId() + " not found."+RESET);
        }
    }

    @Override
    public Optional<Course> findById(Long id) throws SQLException , NotFoundException{
        PreparedStatement pr = this.database.getPreparedStatement(Constant.FIND_COURSE_BY_ID);
        pr.setLong(1,id);
        ResultSet resultSet = pr.executeQuery();
        Optional<Course> optional = Optional.empty();
        while (resultSet.next()){
            Course course = new Course();
            course.setId(resultSet.getLong("course_id"));
            course.setTitle(resultSet.getString("course_title"));
            course.setUnite(resultSet.getInt("course_unit"));
            optional = Optional.of(course);
        }
        if (optional.isPresent()){
        return optional;
        }
        else
            throw new NotFoundException(RED+"Course with ID " + id + " not found."+RESET);
    }

    @Override
    public void delete(Long id) throws SQLException, NotFoundException {
        if (findById(id).isPresent()) {
            Long examIdForDeleteStudentExam = findExamIdForDeleteExam(id);
            PreparedStatement pr = database.getPreparedStatement(Constant.DELETE_STUDENT_EXAM_BECUEASE_EXAM);
            pr.setLong(1, examIdForDeleteStudentExam);
            pr.executeUpdate();
            pr = database.getPreparedStatement(Constant.DELETE_COURSE_FROM_EXAM);
            pr.setLong(1, id);
            pr.executeUpdate();
            pr = database.getPreparedStatement(Constant.DELETE_COURSE_FROM_STUDENT_COURSE);
            pr.setLong(1, id);
            pr.executeUpdate();
            pr = database.getPreparedStatement(Constant.DELETE_COURSE_FROM_TEACHER);
            pr.setLong(1, id);
            pr.executeUpdate();
            pr = database.getPreparedStatement(Constant.DELETE_COURSE);
            pr.setLong(1, id);
            pr.executeUpdate();
        } else {
            throw new NotFoundException(RED+"Course with ID " + id + " not found."+RESET);
        }
    }

    private Long findExamIdForDeleteExam (Long id) throws SQLException {
        PreparedStatement preparedStatement = this.database.getPreparedStatement(Constant.FIND_EXAM_ID_FOR_DELETE_FROM_STUDENT_EXAM);
        preparedStatement.setLong(1,id);
        ResultSet resultSet = preparedStatement.executeQuery();
        Long examId = 0L;
        while (resultSet.next()){
            examId = resultSet.getLong("exam_id");
        }
        return examId;
    }

    @Override
    public Set<Course> getAll() throws SQLException {
        ResultSet resultSet = this.database.getStatement().executeQuery(Constant.GET_ALL_COURSES);
        Set<Course> courses = new HashSet<>();
        while (resultSet.next()) {
            Course course = new Course(
                    resultSet.getLong("course_id"),
                    resultSet.getString("course_title"),
                    resultSet.getInt("course_unit")
            );
            courses.add(course);
        }
        return courses;
    }

    @Override
    public int getCount() throws SQLException {
        return getAll().size();
    }

    @Override
    public String getCourseTitleOfExamByExamId(Long id) throws SQLException {
        PreparedStatement pr = this.database.getPreparedStatement(Constant.GET_COURSE_OF_EXAM_BY_EXAM_ID);
        pr.setLong(1,id);
        ResultSet resultSet = pr.executeQuery();
        String courseTitle = "";
        while (resultSet.next()) {
            courseTitle = resultSet.getString("course_title");
        }
        return courseTitle;
    }

    @Override
    public void studentGetCourse(Long studentId, Long courseId) throws SQLException {
        PreparedStatement pr = this.database.getPreparedStatement(Constant.STUDENT_GET_COURSE);
        pr.setLong(1,studentId);
        pr.setLong(2,courseId);
        pr.executeUpdate();
    }

    @Override
    public void deleteStudentCourse(Long id) throws SQLException {
        PreparedStatement pr = this.database.getPreparedStatement(Constant.DELETE_STUDENT_COURSE);
        pr.setLong(1,id);
        pr.executeUpdate();
    }

    @Override
    public Set<String> getAllCourseTitleOfStudent(Long studentId) throws SQLException {
        PreparedStatement pr = this.database.getPreparedStatement(Constant.GET_ALL_COURSES_TITLE_OF_STUDENT);
        pr.setLong(1,studentId);
        ResultSet resultSet = pr.executeQuery();
        Set<String> allCourseTitle = new HashSet<>();
        while (resultSet.next()){
            String courseTitle = resultSet.getString("course_title");
            allCourseTitle.add(courseTitle);
        }
        return allCourseTitle;
    }
}
