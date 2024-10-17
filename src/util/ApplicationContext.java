package util;

import repository.*;
import repository.impl.*;
import service.CourseService;
import service.ExamService;
import service.StudentService;
import service.TeacherService;
import service.impl.*;

public class ApplicationContext {
    public static String [] starterMenu = {"Sign In","EXIT"};
    public static String [] managerMenu = {"STUDENT","TEACHER","COURSES","EXAMS","EXIT"};
    public static String [] managerStudentsMenu = {"CREAT ","UPDATE","DELETE","ALL STUDENTS","COUNT OF STUDENTS","FIND STUDENT BY ID","EXIT"};
    public static String [] managerTeacherMenu = {"CREAT ","UPDATE","DELETE","ALL TEACHER","COUNT OF TEACHER","FIND TEACHER BY ID","EXIT"};
    public static String [] managerCoursesMenu = {"CREAT ","UPDATE","DELETE","ALL COURSES","COUNT OF COURSES","FIND COURSES BY ID","EXIT"};
    public static String [] managerExamsMenu = {"CREAT ","UPDATE","DELETE","ALL EXAMS","COUNT OF EXAMS","FIND EXAMS BY ID","EXIT"};
    public static String [] studentMenu = {"GIVE COURSE","DELETE YOUR COURSES","SEE YOUR EXAMS","ALL YOUR COURSES","EXIT"};
    public static String [] teacherMenu = {"CREAT A EXAM ","UPDATE EXAM","EXIT"};


    private static StudentRepository studentRepository;
    private static TeacherRepository teacherRepository;
    private static CourseRepository courseRepository;
    private static ExamRepository examRepository;
    private static ManagerRepository managerRepository;
    private static UserRepository userRepository;
    private static StudentService studentService;
    private static TeacherService teacherService;
    private static CourseService courseService;
    private static ExamService examService;
    private static LoginService loginService;

    static {
        studentRepository = new StudentRepositoryImpl();
        teacherRepository = new TeacherRepositoryImpl();
        courseRepository = new CourseRepositoryImpl();
        examRepository = new ExamRepositoryImpl();
        managerRepository = new ManagerRepositoryImpl();
        userRepository = new UserRepository();
        loginService = new LoginService(userRepository , studentRepository , teacherRepository , managerRepository);
        studentService = new StudentServiceImpl(studentRepository);
        teacherService = new TeacherServiceImpl(teacherRepository, examRepository);
        courseService = new CourseServiceImpl(courseRepository);
        examService = new ExamServiceImpl(examRepository,teacherRepository,courseRepository);
    }

    public static StudentService getStudentService() {
        return studentService;
    }

    public static TeacherService getTeacherService() {
        return teacherService;
    }

    public static CourseService getCourseService() {
        return courseService;
    }

    public static ExamService getExamService() {
        return examService;
    }

    public static LoginService getLoginService() {
        return loginService;
    }
}
