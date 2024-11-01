package ui;

import model.*;
import model.dto.*;
import model.enumtype.UserType;
import util.ApplicationContext;
import util.Printer;
import util.Utility;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import static util.Color.*;

public class Menu {
    public static void startProject() {
        Printer.printMenu(ApplicationContext.starterMenu, "WELCOME");
        int choice = Utility.getInt("Enter your choice : ");
        switch (choice) {
            case 1:
                singIn();
                break;
            case 2:
                break;
        }
    }

    private static void singIn() {
        String username = Utility.getString("Enter your username : ");
        String password = Utility.getString("Enter your password : ");
        Optional<User> loginUser = ApplicationContext.getLoginService().login(username, password);
        if (loginUser.isPresent()){
        if (loginUser.get().getType().equals(UserType.MANAGER)) {
            managerAccess();
        } else if (loginUser.get().getType().equals(UserType.STUDENT)){
            studentAccess(loginUser.get());
        } else if (loginUser.get().getType().equals(UserType.TEACHER)) {
            teacherAccess(loginUser.get());
        }
        } else {
//            TODO CHANG PASSWORD
            Printer.printError("your username or password is wrong please try again!");
            Printer.printDescription("1.try again");
            Printer.printDescription("2.Chang password");
            int ch = Utility.getInt("Enter your choice : ");
            switch (ch){
                case 1 :
                    singIn();
                    break;
                case 2 :
                    changPassword();
                    Printer.printDescription("login again");
                    singIn();
                    break;
            }
        }
    }

    private static void changPassword() {
        String nationalCode = Utility.getString("Enter your national code for changing password : ");
        String newPassword = Utility.getString("Enter your newPassword : ");
        ApplicationContext.getLoginService().updatePassword(newPassword , nationalCode);
    }

    private static void teacherAccess(User teacher) {
        Printer.printMenu(ApplicationContext.teacherMenu , "Welcome "+teacher.getFirstName()+" "+teacher.getLastName());
        int choice = Utility.getInt("Enter your choice : ");
        switch (choice) {
            case 1:
                String examDate = Utility.getString("Enter date of exam : ");
                String[] splitExamDate = examDate.split("-");
                Exam exam = new Exam();
                exam.setTeacherId(teacher.getId());
                exam.setCourseId(ApplicationContext.getTeacherService().getCourseIdThatTeacherTeach(teacher.getId()));
                exam.setDate(new Date(
                        Integer.parseInt(splitExamDate[0])-1900,
                        Integer.parseInt(splitExamDate[1])-1,
                        Integer.parseInt(splitExamDate[2])
                ));

                ApplicationContext.getTeacherService().creatExam(exam);
                break;
            case 2:
                updateExam();
                break;
            case 3:
                Long studentId = Utility.getLong("Enter student ID : ");
                Double studentGrade = Utility.getDouble("Enter grade of student : ");
                Long examIdThatTeacherTeach = ApplicationContext.getExamService().getExamIdThatTeacherTeach(teacher.getId());
                ApplicationContext.getExamService().addGradeForStudent(studentId , examIdThatTeacherTeach , studentGrade);
                break;
            case 4:
                startProject();
                break;
        }
        teacherAccess(teacher);
    }

    private static void studentAccess(User student) {
        Printer.printMenu(ApplicationContext.studentMenu , "Welcome "+student.getFirstName()+" "+student.getLastName());
        int choice = Utility.getInt("Enter your choice : ");
        switch (choice) {
            case 1:
                Set<ExamDto> allExamOfStudent = ApplicationContext.getExamService().getAllExamOfStudent(student.getId());
                System.out.println(String.format(CYAN + "Exams\n" + "%-5s | %-20s | %-12s | %-12s",
                        "Id", "teacher Name", "course title", "exam Date" + RESET));
                Printer.printLine(35);
                Printer.printSet(allExamOfStudent);
                Printer.printLine(35);
                break;
            case 2:
                Set<String> allCourseTitleOfStudent = ApplicationContext.getCourseService().getAllCourseTitleOfStudent(student.getId());
                Printer.printLine1("~~~~~~~~~~~~~~~~~~~~~");
                Printer.printSet(allCourseTitleOfStudent);
                Printer.printLine1("~~~~~~~~~~~~~~~~~~~~~");
                break;
            case 3:
                Set<StudentCourseGrades> studentCourseAndGrades = ApplicationContext.getStudentService().seeStudentGrad(student.getId());
                System.out.println(String.format(CYAN + "%-12s | %-12s", "courseTitle", "grade"+ RESET));
                Printer.printLine(20);
                Printer.printSet(studentCourseAndGrades);
                Printer.printLine(20);
                break;
            case 4:
                startProject();
                break;
        }
        studentAccess(student);
    }

    private static void managerAccess() {
        Printer.printMenu(ApplicationContext.managerMenu, "WELCOME Admin");
        int choice = Utility.getInt("Enter your choice : ");
        switch (choice) {
            case 1:
                managerStudentsMenu();
                break;
            case 2:
                managerTeacherMenu();
                break;
            case 3:
                managerCoursesMenu();
                break;
            case 4:
                managerExamsMenu();
                break;
            case 5:
                startProject();
                break;
        }
        managerAccess();
    }

    private static void managerStudentsMenu() {
        Printer.printMenu(ApplicationContext.managerStudentsMenu, "STUDENT");
        int choice = Utility.getInt("Enter your choice : ");
        switch (choice) {
            case 1:
                creatStudent();
                break;
            case 2:
                updateStudent();
                break;
            case 3:
                deleteStudent();
                break;
            case 4:
                studentGetCourse();
                break;
            case 5:
                printAllStudent();
                break;
            case 6:
                printCountOfStudent();
                break;
            case 7:
                printStudentFindById();
                break;
            case 8:
                managerAccess();
                break;
        }
        managerStudentsMenu();
    }

    private static void studentGetCourse() {
        printAllStudent();
        Long studentId = Utility.getLong("Enter student id for add course : ");
        printAllCourse();
        Long courseId = Utility.getLong("Enter course id for add student : ");
        ApplicationContext.getStudentService().studentGetCourse(studentId , courseId);
    }

    private static void creatStudent() {
        String firstName = Utility.getString("Enter student first name : ");
        String lastName = Utility.getString("Enter student last name : ");
        String nationalCode = Utility.getString("Enter student national code : ");
        String dob = Utility.getString("Enter student date of birth YYYY-MM-DD : ");
        String[] splitDob = dob.split("-");
        String entryDate = Utility.getString("Enter student entry date : ");
        String[] splitEntryDate = entryDate.split("-");
        String username = Utility.getString("Enter student username : ");
        String password = Utility.getString("Enter student password : ");
        Double gpu = Utility.getDouble("Enter student gpu : ");
        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setNationalCode(nationalCode);
        student.setDob(new Date(
                Integer.parseInt(splitDob[0])-1900,
                Integer.parseInt(splitDob[1])-1,
                Integer.parseInt(splitDob[2])
        ));
        student.setEntryDate(new Date(
                Integer.parseInt(splitEntryDate[0])-1900,
                Integer.parseInt(splitEntryDate[1])-1,
                Integer.parseInt(splitEntryDate[2])
        ));
        student.setUsername(username);
        student.setPassword(password);
        student.setGpu(gpu);
        ApplicationContext.getStudentService().creat(student);
        printAllStudent();
    }

    private static void updateStudent() {
        printAllStudent();
        Long id = Utility.getLong("Enter id of student you want update : ");
        String firstName = Utility.getString("Enter student first name : ");
        String lastName = Utility.getString("Enter student last name : ");
        String nationalCode = Utility.getString("Enter student national code : ");
        String dob = Utility.getString("Enter student date of birth YYYY-MM-DD : ");
        String[] splitDob = dob.split("-");
        String entryDate = Utility.getString("Enter student entry date : ");
        String[] splitEntryDate = entryDate.split("-");
        String username = Utility.getString("Enter student username : ");
        String password = Utility.getString("Enter student password : ");
        Double gpu = Utility.getDouble("Enter student gpu : ");
        Student student = new Student();
        student.setId(id);
        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setNationalCode(nationalCode);
        student.setDob(new Date(
                Integer.parseInt(splitDob[0])-1900,
                Integer.parseInt(splitDob[1])-1,
                Integer.parseInt(splitDob[2])
        ));
        student.setEntryDate(new Date(
                Integer.parseInt(splitEntryDate[0])-1900,
                Integer.parseInt(splitEntryDate[1])-1,
                Integer.parseInt(splitEntryDate[2])
        ));
        student.setUsername(username);
        student.setPassword(password);
        student.setGpu(gpu);
        ApplicationContext.getStudentService().update(student);
        printAllStudent();
    }

    private static void deleteStudent() {
        printAllStudent();
        Long studentId = Utility.getLong("Enter id of student you want delete : ");
        ApplicationContext.getStudentService().delete(studentId);
        printAllStudent();
    }

    private static void printStudentFindById() {
        Long id = Utility.getLong("Enter id of student you want find : ");
        Optional<StudentDto> student = ApplicationContext.getStudentService().findById(id);
        if (student.isPresent()) {
            System.out.println(String.format(CYAN + "*** Student *** \n" + "%-5s | %-12s | %-12s | %-14s | %-12s | %-12s | %-12s",
                    "Id", "firstName", "lastName", "nationalCode", "dob", "entryDate", "gpu"));
            Printer.printLine(44);
            System.out.println(BLUE + student.get() + RESET);
            Printer.printLine(44);
        }
    }

    private static void printCountOfStudent() {
        try {
            int count = ApplicationContext.getStudentService().getCount();
            Printer.printLine1("~~~~~~~~~~~~~~~");
            Printer.printDescription("#Students : " + count);
            Printer.printLine1("~~~~~~~~~~~~~~~");
        } catch (SQLException e) {
            System.out.println(RED + e.getMessage() + RESET);
        }
    }

    private static void printAllStudent() {
        Set<StudentDto> all = ApplicationContext.getStudentService().getAll();
        System.out.println(String.format(CYAN + "*** Student *** \n" + "%-5s | %-12s | %-12s | %-14s | %-12s | %-12s | %-12s",
                "Id", "firstName", "lastName", "nationalCode", "dob", "entryDate", "gpu"));
        Printer.printLine(44);
        Printer.printSet(all);
        Printer.printLine(44);
    }


    private static void managerTeacherMenu() {
        Printer.printMenu(ApplicationContext.managerTeacherMenu, "TEACHER");
        int choice = Utility.getInt("Enter your choice :");
        switch (choice) {
            case 1:
                creatTeacher();
                break;
            case 2:
                updateTeacher();
                break;
            case 3:
                deleteTeacher();
                break;
            case 4:
                printAllTeacher();
                break;
            case 5:
                printCountOfTeacher();
                break;
            case 6:
                printTeacherFindById();
                break;
            case 7:
                managerAccess();
                break;
        }
        managerTeacherMenu();
    }

    private static void creatTeacher() {
        String firstName = Utility.getString("Enter teacher first name : ");
        String lastName = Utility.getString("Enter teacher last name : ");
        Long courseId = Utility.getLong("Enter course id of teacher want to teach : ");
        String nationalCode = Utility.getString("Enter teacher national code : ");
        String dob = Utility.getString("Enter teacher date of birth YYYY-MM-DD : ");
        String[] splitDob = dob.split("-");
        String entryDate = Utility.getString("Enter teacher entry date : ");
        String[] splitEntryDate = entryDate.split("-");
        String username = Utility.getString("Enter teacher username : ");
        String password = Utility.getString("Enter teacher password : ");
        Teacher teacher = new Teacher();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setCourseId(courseId);
        teacher.setNationalCode(nationalCode);
        teacher.setDob(new Date(
                Integer.parseInt(splitDob[0])-1900,
                Integer.parseInt(splitDob[1])-1,
                Integer.parseInt(splitDob[2])
        ));
        teacher.setEntryDate(new Date(
                Integer.parseInt(splitEntryDate[0])-1900,
                Integer.parseInt(splitEntryDate[1])-1,
                Integer.parseInt(splitEntryDate[2])
        ));
        teacher.setUsername(username);
        teacher.setPassword(password);
        ApplicationContext.getTeacherService().creat(teacher);
        printAllTeacher();
    }

    private static void updateTeacher() {
        printAllTeacher();
        Long id = Utility.getLong("Enter id of teacher you want update : ");
        String firstName = Utility.getString("Enter teacher first name : ");
        String lastName = Utility.getString("Enter teacher last name : ");
        Long courseId = Utility.getLong("Enter course id of teacher want to teach : ");
        String nationalCode = Utility.getString("Enter teacher national code : ");
        String dob = Utility.getString("Enter teacher date of birth YYYY-MM-DD : ");
        String[] splitDob = dob.split("-");
        String entryDate = Utility.getString("Enter teacher entry date : ");
        String[] splitEntryDate = entryDate.split("-");
        String username = Utility.getString("Enter teacher username : ");
        String password = Utility.getString("Enter teacher password : ");
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        teacher.setCourseId(courseId);
        teacher.setNationalCode(nationalCode);
        teacher.setDob(new Date(
                Integer.parseInt(splitDob[0])-1900,
                Integer.parseInt(splitDob[1])-1,
                Integer.parseInt(splitDob[2])
        ));
        teacher.setEntryDate(new Date(
                Integer.parseInt(splitEntryDate[0])-1900,
                Integer.parseInt(splitEntryDate[1])-1,
                Integer.parseInt(splitEntryDate[2])
        ));
        teacher.setUsername(username);
        teacher.setPassword(password);
        ApplicationContext.getTeacherService().update(teacher);
        printAllTeacher();
    }

    private static void deleteTeacher() {
        printAllTeacher();
        Long teacherId = Utility.getLong("Enter id of teacher you want delete : ");
        ApplicationContext.getTeacherService().delete(teacherId);
        printAllTeacher();

    }

    private static void printTeacherFindById() {
        Long id = Utility.getLong("Enter id of teacher you want find : ");
        Optional<TeacherDto> teacher = ApplicationContext.getTeacherService().findById(id);
        if (teacher.isPresent()) {
            System.out.println(String.format(CYAN + "*** Teacher ***\n" + "%-5s | %-12s | %-12s | %-15s | %-12s | %-12s | %-12s",
                    "Id", "firstName", "lastName", "nationalCode", "dob", "entryDate", "courseTitle" + RESET));
            Printer.printLine(52);
            System.out.println(BLUE + teacher.get() + RESET);
            Printer.printLine(52);
        }
    }

    private static void printCountOfTeacher() {
        try {
            int count = ApplicationContext.getTeacherService().getCount();
            Printer.printLine1("~~~~~~~~~~~~~~~");
            Printer.printDescription("#Teachers : " + count);
            Printer.printLine1("~~~~~~~~~~~~~~~");
        } catch (SQLException e) {
            System.out.println(RED + e.getMessage() + RESET);
        }
    }

    private static void printAllTeacher() {
        Set<TeacherDto> all = ApplicationContext.getTeacherService().getAll();
        System.out.println(String.format(CYAN + "*** Teachers ***\n" + "%-5s | %-12s | %-12s | %-15s | %-12s | %-12s | %-12s",
                "Id", "firstName", "lastName", "nationalCode", "dob", "entryDate", "courseTitle"));
        Printer.printLine(52);
        Printer.printSet(all);
        Printer.printLine(52);
    }


    private static void managerCoursesMenu() {
        Printer.printMenu(ApplicationContext.managerCoursesMenu, "COURSES");
        int choice = Utility.getInt("Enter your choice :");
        switch (choice) {
            case 1:
                creatCourse();
                break;
            case 2:
                updateCourse();
                break;
            case 3:
                deleteCourse();
                break;
            case 4:
                printAllCourse();
                break;
            case 5:
                printCountOfCourse();
                break;
            case 6:
                printCourseFindById();
                break;
            case 7:
                managerAccess();
                break;
        }
        managerCoursesMenu();
    }

    private static void creatCourse() {
        int unite = Utility.getInt("Enter course unit : ");
        String title = Utility.getString("Enter course title : ");
        Course course = new Course();
        course.setUnite(unite);
        course.setTitle(title);
        ApplicationContext.getCourseService().creat(course);
        printAllCourse();
    }

    private static void updateCourse() {
        printAllCourse();
        Long id = Utility.getLong("Enter id of course you want update : ");
        int unite = Utility.getInt("Enter course unit : ");
        String title = Utility.getString("Enter course title : ");
        Course course = new Course();
        course.setId(id);
        course.setUnite(unite);
        course.setTitle(title);
        ApplicationContext.getCourseService().update(course);
        printAllCourse();
    }

    private static void deleteCourse() {
        printAllCourse();
        Long courseId = Utility.getLong("Enter id of course you want delete : ");
        ApplicationContext.getCourseService().delete(courseId);
        printAllCourse();
    }

    private static void printCourseFindById() {
        Long id = Utility.getLong("Enter id of course you want find : ");
        Optional<CourseDto> course = ApplicationContext.getCourseService().findById(id);
        if (course.isPresent()) {
            System.out.println(String.format(CYAN + "Courses\n" + "%-5s | %-12s | %-12s", "Id", "courseTitle", "courseUnit" + RESET));
            Printer.printLine(17);
            System.out.println(BLUE + course.get() + RESET);
            Printer.printLine(17);
        }
    }

    private static void printCountOfCourse() {
        try {
            int count = ApplicationContext.getCourseService().getCount();
            Printer.printLine1("~~~~~~~~~~~~~~~");
            Printer.printDescription("#Courses : " + count);
            Printer.printLine1("~~~~~~~~~~~~~~~");
        } catch (SQLException e) {
            System.out.println(RED + e.getMessage() + RESET);
        }

    }

    private static void printAllCourse() {

        Set<CourseDto> all = ApplicationContext.getCourseService().getAll();
        System.out.println(String.format(CYAN + "Courses\n" + "%-5s | %-12s | %-12s ", "Id", "courseTitle", "courseUnit" + RESET));
        Printer.printLine(17);
        Printer.printSet(all);
        Printer.printLine(17);
    }


    private static void managerExamsMenu() {
        Printer.printMenu(ApplicationContext.managerExamsMenu, "EXAM");
        int choice = Utility.getInt("Enter your choice :");
        switch (choice) {
            case 1:
                creatExam();
                break;
            case 2:
                updateExam();
                break;
            case 3:
                deleteExam();
                break;
            case 4:
                printAllExam();
                break;
            case 5:
                printCountOfExam();
                break;
            case 6:
                printExamFindById();
                break;
            case 7:
                managerAccess();
                break;
        }
        managerExamsMenu();
    }

    private static void deleteExam() {
        printAllExam();
        Long examId = Utility.getLong("Enter id of exam you want delete : ");
        ApplicationContext.getExamService().delete(examId);
        printAllExam();
    }

    private static void updateExam() {
        printAllExam();
        Long id = Utility.getLong("Enter id of exam you want update : ");
        printAllTeacher();
        Long teacherId = Utility.getLong("Enter teacher id : ");
        printAllCourse();
        Long courseId = Utility.getLong("Enter course id : ");
        String examDate = Utility.getString("Enter exam date : ");
        String[] splitExamDate = examDate.split("-");
        Exam exam = new Exam();
        exam.setId(id);
        exam.setTeacherId(teacherId);
        exam.setCourseId(courseId);
        exam.setDate(new Date(
                Integer.parseInt(splitExamDate[0])-1900,
                Integer.parseInt(splitExamDate[1])-1,
                Integer.parseInt(splitExamDate[2])
        ));
        ApplicationContext.getExamService().update(exam);
        printAllExam();
    }

    private static void creatExam() {
        printAllTeacher();
        Long teacherId = Utility.getLong("Enter id of the teacher who will take this exam : ");
        printAllCourse();
        Long courseId = Utility.getLong("Enter the ID of the course from which the exam is to be taken : ");
        String examDate = Utility.getString("Enter date of exam : ");
        String[] splitExamDate = examDate.split("-");
        Exam exam = new Exam();
        exam.setTeacherId(teacherId);
        exam.setCourseId(courseId);
        exam.setDate(new Date(
                Integer.parseInt(splitExamDate[0])-1900,
                Integer.parseInt(splitExamDate[1])-1,
                Integer.parseInt(splitExamDate[2])
        ));
        ApplicationContext.getExamService().creat(exam);
        printAllExam();
    }

    private static void printCountOfExam() {
        try {
            int count = ApplicationContext.getExamService().getCount();
            Printer.printLine1("~~~~~~~~~~~~~~~");
            Printer.printDescription("#Exams : " + count);
            Printer.printLine1("~~~~~~~~~~~~~~~");
        } catch (SQLException e) {
            System.out.println(RED + e.getMessage() + RESET);
        }
    }

    private static void printExamFindById() {
        Long id = Utility.getLong("Enter id of exam you want find : ");
        Optional<ExamDto> exam = ApplicationContext.getExamService().findById(id);
        if (exam.isPresent()) {
            System.out.println(String.format(CYAN + "Exams\n" + "%-5s | %-20s | %-12s | %-12s\n",
                    "Id", "teacherFullName", "courseTitle", "examDate" + RESET));
            Printer.printLine(35);
            System.out.println(exam.get());
            Printer.printLine(35);
        }
    }

    private static void printAllExam() {
        Set<ExamDto> all = ApplicationContext.getExamService().getAll();
        System.out.println(String.format(CYAN + "Exams\n" + "%-5s | %-20s | %-12s | %-12s\n",
                "Id", "teacherFullName", "courseTitle", "examDate" + RESET));
        Printer.printLine(35);
        Printer.printSet(all);
        Printer.printLine(35);

    }
}
