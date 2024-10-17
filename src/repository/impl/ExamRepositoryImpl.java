package repository.impl;

import database.Database;
import exception.NotFoundException;
import model.Exam;
import repository.ExamRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static util.Color.RED;
import static util.Color.RESET;

public class ExamRepositoryImpl implements ExamRepository {
    private final Database database = new Database();

    @Override
    public void creat(Exam entity) throws SQLException {
        PreparedStatement pr = database.getPreparedStatement(Constant.CREATE_EXAM);
        pr.setLong(1, entity.getTeacherId());
        pr.setLong(2, entity.getCourseId());
        pr.setDate(3, entity.getDate());
        pr.executeUpdate();
    }

    @Override
    public void update(Exam entity) throws SQLException, NotFoundException {
        Optional<Exam> exam = findById(entity.getId());
        if (exam.isPresent()) {
            PreparedStatement pr = database.getPreparedStatement(Constant.UPDATE_EXAM);
            pr.setLong(1, entity.getTeacherId());
            pr.setLong(2, entity.getCourseId());
            pr.setDate(3, entity.getDate());
            pr.setLong(4, entity.getId());
            pr.executeUpdate();
            pr.close();
        } else {
            throw new NotFoundException(RED+"Exam with ID " + entity.getId() + " not found."+RESET);
        }
    }

    @Override
    public Optional<Exam> findById(Long id) throws SQLException, NotFoundException {
        PreparedStatement pr = database.getPreparedStatement(Constant.FIND_EXAM_BY_ID);
        pr.setLong(1, id);
        ResultSet resultSet = pr.executeQuery();
        Optional<Exam> optional = Optional.empty();
        while (resultSet.next()) {
            Exam exam = new Exam();
            exam.setId(resultSet.getLong("exam_id"));
            exam.setDate(resultSet.getDate("exam_date"));
            exam.setTeacherId(resultSet.getLong("teacher_id"));
            exam.setCourseId(resultSet.getLong("course_id"));
            optional = Optional.of(exam);
        }
        if (optional.isPresent()) {
            return optional;
        }
        throw new NotFoundException(RED+"Exam with ID " + id + " not found."+RESET);
    }

    @Override
    public void delete(Long id) throws SQLException, NotFoundException {
        if (findById(id).isPresent()) {
            PreparedStatement pr = database.getPreparedStatement(Constant.DELETE_STUDENT_EXAM_FOR_DELETE_EXAM);
            pr.setLong(1, id);
            pr.executeUpdate();
            pr = database.getPreparedStatement(Constant.DELETE_EXAM);
            pr.setLong(1, id);
            pr.executeUpdate();
        } else {
            throw new NotFoundException(RED+"Exam with ID " + id + " not found."+RESET);
        }
    }

    @Override
    public Set<Exam> getAll() throws SQLException {
        ResultSet resultSet = this.database.getStatement().executeQuery(Constant.GET_ALL_EXAMS);
        Set<Exam> exams = new HashSet<>();
        while (resultSet.next()) {
            Exam exam = new Exam(
                    resultSet.getLong("exam_id"),
                    resultSet.getDate("exam_date"),
                    resultSet.getLong("teacher_id"),
                    resultSet.getLong("course_id")
            );
            exams.add(exam);
        }
        return exams;
    }

    @Override
    public int getCount() throws SQLException {
        return getAll().size();
    }

    @Override
    public Set<Exam> getAllExamOfStudent(Long userId) throws SQLException {
        PreparedStatement pr = this.database.getPreparedStatement(Constant.GET_ALL_EXAM_OF_STUDENT);
        pr.setLong(1,userId);
        ResultSet resultSet = pr.executeQuery();
        Set<Exam> studentExams = new HashSet<>();
        while (resultSet.next()){
            Exam exam = new Exam(
                    resultSet.getLong("exam_id"),
                    resultSet.getDate("exam_date"),
                    resultSet.getLong("teacher_id"),
                    resultSet.getLong("course_id")
            );
            studentExams.add(exam);
        }
        return studentExams;
    }

}
