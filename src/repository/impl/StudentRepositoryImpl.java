package repository.impl;

import database.Database;
import exception.NotFoundException;
import exception.RepetitiveUsernameException;
import model.Student;
import model.dto.StudentCourseAndGrades;
import repository.StudentRepository;
import util.Printer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static util.Color.RED;
import static util.Color.RESET;

public class StudentRepositoryImpl implements StudentRepository {
    private final Database database = new Database();

    @Override
    public void creat(Student entity) throws SQLException , RepetitiveUsernameException {
        checkRepetitiveUsername(entity.getUsername());
        PreparedStatement pr = database.getPreparedStatementAndColumnIndexes(Constant.CREAT_USER_STUDENT, Statement.RETURN_GENERATED_KEYS);
        pr.setString(1, entity.getFirstName());
        pr.setString(2, entity.getLastName());
        pr.setString(3, entity.getNationalCode());
        pr.setDate(4, entity.getDob());
        pr.setDate(5, entity.getEntryDate());
        pr.setString(6, entity.getUsername());
        pr.setString(7, entity.getPassword());
        pr.executeUpdate();
        ResultSet rs = pr.getGeneratedKeys();
        long userId = 0;
        if (rs.next()) {
            userId = rs.getLong(1);
        }
        pr = database.getPreparedStatement(Constant.CREATE_STUDENT);
        pr.setLong(1, userId);
        pr.setDouble(2, entity.getGpu());
        pr.executeUpdate();
    }
    private void checkRepetitiveUsername(String username) throws SQLException  {
        TeacherRepositoryImpl.checkRepetitiveUsername(username, this.database);
//        this method is in TeacherRepositoryImpl class because it's repetitive method and I did it for dry(don't repeat your self)  :)
    }

    @Override
    public void update(Student entity) throws SQLException, NotFoundException {
        Optional<Student> student = findById(entity.getId());
        Long userIdFromStudent = getUserIdFromStudent(entity.getId());
        if (student.isPresent()) {
            PreparedStatement pr = database.getPreparedStatement(Constant.UPDATE_USER_STUDENT);
            pr.setString(1, entity.getFirstName());
            pr.setString(2, entity.getLastName());
            pr.setString(3, entity.getNationalCode());
            pr.setDate(4, entity.getDob());
            pr.setDate(5, entity.getEntryDate());
            pr.setString(6, entity.getUsername());
            pr.setString(7, entity.getPassword());
            pr.setLong(8, userIdFromStudent);
            pr.executeUpdate();
            pr = database.getPreparedStatement(Constant.UPDATE_STUDENT);
            pr.setLong(1, userIdFromStudent);
            pr.setDouble(2, entity.getGpu());
            pr.setLong(3, entity.getId());
            pr.executeUpdate();
            pr.close();
        } else {
            throw new NotFoundException(RED+"Student with ID " + entity.getId() + " not found."+RESET);
        }
    }

    private Long getUserIdFromStudent(Long id) throws SQLException {
        PreparedStatement pr = this.database.getPreparedStatement(Constant.GET_USER_ID_FROM_STUDENT);
        pr.setLong(1, id);
        ResultSet resultSet = pr.executeQuery();
        while (resultSet.next()) {
            id = resultSet.getLong("id");
        }
        return id;
    }

    @Override
    public Optional<Student> findById(Long id) throws SQLException , NotFoundException{
        PreparedStatement pr = database.getPreparedStatement(Constant.FIND_STUDENT_BY_ID);
        pr.setLong(1, id);
        ResultSet studentResultSet = pr.executeQuery();
        pr = database.getPreparedStatement(Constant.GET_USER_DATA_FOR_FIND);
        pr.setLong(1,id);
        ResultSet userResultSet = pr.executeQuery();
        Optional<Student> optional = Optional.empty();
        while (studentResultSet.next() && userResultSet.next()) {
            Student student = new Student();
            student.setId(studentResultSet.getLong("user_id"));
            student.setFirstName(userResultSet.getString("first_name"));
            student.setLastName(userResultSet.getString("last_name"));
            student.setDob(userResultSet.getDate("dob"));
            student.setNationalCode(userResultSet.getString("national_code"));
            student.setGpu(studentResultSet.getDouble("gpu"));
            student.setEntryDate(userResultSet.getDate("entry_date"));
            student.setUsername(userResultSet.getString("username"));
            student.setPassword(userResultSet.getString("password"));
            optional = Optional.of(student);
        }
        if (optional.isPresent()) {
            return optional;
        } else
            throw new NotFoundException(RED+"Student with ID " + id + " not found."+RESET);
    }

    @Override
    public void delete(Long id) throws SQLException, NotFoundException {
        if (findById(id).isPresent()) {
            PreparedStatement pr = database.getPreparedStatement(Constant.DELETE_STUDENT_COURSE_BY_STUDENT_ID);
            pr.setLong(1, id);
            pr.executeUpdate();
            pr = database.getPreparedStatement(Constant.DELETE_STUDENT_EXAM);
            pr.setLong(1, id);
            pr.executeUpdate();
            pr = database.getPreparedStatement(Constant.DELETE_STUDENT);
            pr.setLong(1, id);
            pr.executeUpdate();
            pr = database.getPreparedStatement(Constant.DELETE_USER);
            pr.setLong(1, id);
            pr.executeUpdate();
        } else {
            throw new NotFoundException(RED+"Student with ID " + id + " not found."+RESET);
        }
    }

    @Override
    public Set<Student> getAll() throws SQLException {
        ResultSet studentResultSet = this.database.getStatement().executeQuery(Constant.GET_ALL_STUDENTS);
        ResultSet userDataResultSet = this.database.getStatement().executeQuery(Constant.GET_STUDENT_USER_DATA);
        Set<Student> students = new HashSet<>();
        while (studentResultSet.next() && userDataResultSet.next()) {
            Student student = new Student();
            student.setId(studentResultSet.getLong("user_id"));
            student.setFirstName(userDataResultSet.getString("first_name"));
            student.setLastName(userDataResultSet.getString("last_name"));
            student.setDob(userDataResultSet.getDate("dob"));
            student.setNationalCode(userDataResultSet.getString("national_code"));
            student.setGpu(studentResultSet.getDouble("gpu"));
            student.setEntryDate(userDataResultSet.getDate("entry_date"));
            student.setUsername(userDataResultSet.getString("username"));
            student.setPassword(userDataResultSet.getString("password"));
            students.add(student);
        }
        return students;
    }

    @Override
    public int getCount() throws SQLException {
        return getAll().size();
    }

    @Override
    public void studentGetCourse(Long studentId, Long courseId) throws SQLException {
        PreparedStatement pr = this.database.getPreparedStatement(Constant.STUDENT_GET_COURSE);
        pr.setLong(1,studentId);
        pr.setLong(2,courseId);
        pr.executeUpdate();
        if (getExamIdForAddStudent().isPresent()){
            PreparedStatement ps = this.database.getPreparedStatement(Constant.ADD_STUDENT_TO_EXAM);
            ps.setLong(1,studentId);
            ps.setLong(2,getExamIdForAddStudent().get());
            ps.executeUpdate();
        } else {
            Printer.printError("There is no exam for this course!");
        }
    }

    @Override
    public Set<StudentCourseAndGrades> seeStudentGrad(Long studentId) throws SQLException {
        PreparedStatement pr = this.database.getPreparedStatement(Constant.SEE_GRAD_OF_STUDENT);
        pr.setLong(1,studentId);
        ResultSet resultSet = pr.executeQuery();
        Set<StudentCourseAndGrades> studentCourseAndGrades = new HashSet<>();
        while (resultSet.next()){
            StudentCourseAndGrades student = new StudentCourseAndGrades();
            student.setCourseTitle(resultSet.getString("course_title"));
            student.setGrade(resultSet.getDouble("student_grade"));
            studentCourseAndGrades.add(student);
        }
        return studentCourseAndGrades;
    }

    private Optional<Long> getExamIdForAddStudent() throws SQLException{
        ResultSet resultSet = this.database.getStatement().executeQuery(Constant.GET_EXAM_ID_FOR_ADD_STUDENT);
        Optional<Long> examId = Optional.empty();
        while (resultSet.next()){
            examId = Optional.of(resultSet.getLong("exam_id"));
        }
        return examId;
    }
}
