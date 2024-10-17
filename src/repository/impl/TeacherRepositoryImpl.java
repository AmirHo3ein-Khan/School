package repository.impl;

import database.Database;
import exception.NotFoundException;
import model.Teacher;
import repository.TeacherRepository;
import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static util.Color.RED;
import static util.Color.RESET;

public class TeacherRepositoryImpl implements TeacherRepository {
    private final Database database = new Database();

    @Override
    public void creat(Teacher entity) throws SQLException {
        PreparedStatement pr = database.getPreparedStatementAndColumnIndexes(Constant.CREAT_USER_TEACHER, Statement.RETURN_GENERATED_KEYS);
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
        pr = database.getPreparedStatement(Constant.CREATE_TEACHER);
        pr.setLong(1, userId);
        pr.setLong(2, entity.getCourseId());
        pr.executeUpdate();
    }

    @Override
    public void update(Teacher entity) throws SQLException, NotFoundException {
        Optional<Teacher> teacher = findById(entity.getId());
        Long userIdFromTeacher = getUserIdFromTeacher(entity.getId());
        if (teacher.isPresent()) {
            PreparedStatement pr = database.getPreparedStatement(Constant.UPDATE_USER_TEACHER);
            pr.setString(1, entity.getFirstName());
            pr.setString(2, entity.getLastName());
            pr.setString(3, entity.getNationalCode());
            pr.setDate(4, entity.getDob());
            pr.setDate(5, entity.getEntryDate());
            pr.setString(6, entity.getUsername());
            pr.setString(7, entity.getPassword());
            pr.setLong(8, userIdFromTeacher);
            pr.executeUpdate();
            pr = database.getPreparedStatement(Constant.UPDATE_TEACHER);
            pr.setLong(1, userIdFromTeacher);
            pr.setLong(2, entity.getCourseId());
            pr.setLong(3, entity.getId());
            pr.executeUpdate();
            pr.close();
        } else {
            throw new NotFoundException(RED+"Teacher with ID " + entity.getId() + " not found."+RESET);
        }
    }

    private Long getUserIdFromTeacher(Long id) throws SQLException {
        PreparedStatement pr = this.database.getPreparedStatement(Constant.GET_USER_ID_FROM_TEACHER);
        pr.setLong(1, id);
        ResultSet resultSet = pr.executeQuery();
        while (resultSet.next()) {
            id = resultSet.getLong("id");
        }
        return id;
    }

    @Override
    public Optional<Teacher> findById(Long id) throws SQLException , NotFoundException{
        PreparedStatement pr = database.getPreparedStatement(Constant.FIND_TEACHER_BY_ID);
        pr.setLong(1, id);
        ResultSet teacherResultSet = pr.executeQuery();
        pr = database.getPreparedStatement(Constant.GET_USER_DATA_FOR_FIND);
        pr.setLong(1,id);
        ResultSet userResultSet = pr.executeQuery();
        Optional<Teacher> optional = Optional.empty();
        while (teacherResultSet.next() && userResultSet.next()) {
            Teacher teacher = new Teacher();
            teacher.setId(teacherResultSet.getLong("user_id"));
            teacher.setCourseId(teacherResultSet.getLong("course_id"));
            teacher.setFirstName(userResultSet.getString("first_name"));
            teacher.setLastName(userResultSet.getString("last_name"));
            teacher.setDob(userResultSet.getDate("dob"));
            teacher.setNationalCode(userResultSet.getString("national_code"));
            teacher.setEntryDate(userResultSet.getDate("entry_date"));
            teacher.setUsername(userResultSet.getString("username"));
            teacher.setPassword(userResultSet.getString("password"));
            optional = Optional.of(teacher);
        }
        if (optional.isPresent()) {
        return optional;
        } else
            throw new NotFoundException(RED+"Teacher with ID " + id + " not found."+RESET);
    }

    @Override
    public void delete(Long id) throws SQLException, NotFoundException {
        if (findById(id).isPresent()) {
            PreparedStatement pr = this.database.getPreparedStatement(Constant.DELETE_EXAM_FOR_TEACHER);
            pr.setLong(1, id);


            pr = database.getPreparedStatement(Constant.DELETE_TEACHER);
            pr.setLong(1, id);
            pr.executeUpdate();
            pr = database.getPreparedStatement(Constant.DELETE_USER);
            pr.setLong(1, id);
            pr.executeUpdate();
        } else {
            throw new NotFoundException(RED+"Teacher with ID " + id + " not found."+RESET);
        }
    }

    @Override
    public Set<Teacher> getAll() throws SQLException {
        ResultSet teacherResultSet = this.database.getStatement().executeQuery(Constant.GET_ALL_TEACHERS);
        ResultSet userDataResultSet = this.database.getStatement().executeQuery(Constant.GET_TEACHER_USER_DATA);
        Set<Teacher> teachers = new HashSet<>();
        while (teacherResultSet.next() && userDataResultSet.next()) {
            Teacher teacher = new Teacher();
            teacher.setId(teacherResultSet.getLong("user_id"));
            teacher.setCourseId(teacherResultSet.getLong("course_id"));
            teacher.setFirstName(userDataResultSet.getString("first_name"));
            teacher.setLastName(userDataResultSet.getString("last_name"));
            teacher.setDob(userDataResultSet.getDate("dob"));
            teacher.setNationalCode(userDataResultSet.getString("national_code"));
            teacher.setEntryDate(userDataResultSet.getDate("entry_date"));
            teacher.setUsername(userDataResultSet.getString("username"));
            teacher.setPassword(userDataResultSet.getString("password"));
            teachers.add(teacher);
        }
        return teachers;
    }

    @Override
    public int getCount() throws SQLException {
        return getAll().size();
    }

    @Override
    public String getCourseTitleThatTeacherTeach(Long id) throws SQLException {
        PreparedStatement pr = this.database.getPreparedStatement(Constant.GET_COURSE_THAT_TEACHER_TEACH);
        pr.setLong(1, id);
        ResultSet resultSet = pr.executeQuery();
        String courseTitleThatTeacherTeach = "";
        while (resultSet.next()) {
            courseTitleThatTeacherTeach = resultSet.getString("course_title");
        }
        return courseTitleThatTeacherTeach;
    }

    @Override
    public String getTeacherFullNameOfTakenExamByExamId(Long id) throws SQLException {
        PreparedStatement pr = this.database.getPreparedStatement(Constant.GET_FULL_NAME_OF_TEACHER_THAT_TAKEN_EXAM);
        pr.setLong(1, id);
        ResultSet resultSet = pr.executeQuery();
        String teacherFullName = "";
        while (resultSet.next()) {
            teacherFullName = resultSet.getString("fullname");
        }
        return teacherFullName;
    }

}
