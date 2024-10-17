package service.impl;

import exception.NotFoundException;
import model.Manager;
import model.Student;
import model.Teacher;
import model.User;
import model.enumtype.UserType;
import repository.ManagerRepository;
import repository.StudentRepository;
import repository.TeacherRepository;
import repository.impl.UserRepository;
import util.Printer;

import java.sql.SQLException;
import java.util.Optional;

public class LoginService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final ManagerRepository managerRepository;

    public LoginService(UserRepository userRepository, StudentRepository studentRepository,
                        TeacherRepository teacherRepository, ManagerRepository managerRepository) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.managerRepository = managerRepository;
    }

    public Optional<User> login (String username , String password) {
        try {
            String userTypeWithUsernameAndPassword = this.userRepository.findUserTypeWithUsernameAndPassword(username, password);
            Long userIdWithUsernameAndPassword = this.userRepository.findUserIdWithUsernameAndPassword(username, password);
            if (userTypeWithUsernameAndPassword.equals(UserType.STUDENT.name())){
                Optional<Student> student = studentRepository.findById(userIdWithUsernameAndPassword);
                User s = student.get();
                return Optional.of(s);
            } else if (userTypeWithUsernameAndPassword.equals(UserType.TEACHER.name())){
                Optional<Teacher> teacher = this.teacherRepository.findById(userIdWithUsernameAndPassword);
                User t = teacher.get();
                return Optional.of(t);
            } else if (userTypeWithUsernameAndPassword.equals(UserType.MANAGER.name())) {
                Optional<Manager> manager = this.managerRepository.findById(userIdWithUsernameAndPassword);
                User m = manager.get();
                return Optional.of(m);
            }
        } catch (SQLException | NotFoundException e){
            Printer.printError(e.getMessage());
        }
        return Optional.empty();
    }
}
