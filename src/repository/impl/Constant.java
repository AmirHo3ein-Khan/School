package repository.impl;

public class Constant {
    //Student Queries
    //language=SQl
    protected static final String CREAT_USER_STUDENT =
            "insert into users (first_name, last_name, national_code, dob, entry_date, username, password, user_type)\n" +
                    "values (?,?,?,?,?,?,?,'STUDENT');";
    //language=SQl
    protected static final String UPDATE_USER_STUDENT =
            "update users\n" +
                    "set (first_name, last_name, national_code, dob, entry_date, username, password, user_type) = (?,?,?,?,?,?,?,'STUDENT')\n" +
                    "where id = ?;";
    //language=SQl
    protected static final String CREATE_STUDENT =
            "insert into student (user_id, gpu)\n" +
                    "values (?,?);";
    //language=SQl
    protected static final String UPDATE_STUDENT =
            "update student s \n" +
                    "set (user_id, gpu) = (?,?)\n" +
                    "where s.user_id = ?;";
    //language=SQL
    protected static final String FIND_STUDENT_BY_ID = "select * from student s where s.user_id = ?";
    //language=SQl
    protected static final String DELETE_STUDENT = "delete from student s where s.user_id = ?;";
    //language=SQl
    protected static final String DELETE_STUDENT_COURSE_BY_STUDENT_ID = "delete from course_student where student_id = ?;";
    //language=SQl
    protected static final String DELETE_STUDENT_EXAM = "delete from student_exam where student_id = ?;";
    //language=SQL
    protected static final String GET_ALL_STUDENTS = "select * from student ;";
    //language=SQL
    protected static final String GET_STUDENT_USER_DATA =
            "select * from users u inner join student s on u.id = s.user_id;";
    //language=SQL
    protected static final String GET_USER_ID_FROM_STUDENT =
            "select u.id from student s inner join users u on s.user_id = u.id where s.user_id = ?;";
    //***********************************************************************************************

    //Query for student and teacher
    //language=SQl
    protected static final String DELETE_USER = "delete from users u where u.id = ?;";
    //language=SQL
    protected static final String GET_USER_DATA_FOR_FIND =
            "select * from users u where u.id = ?";
    //***********************************************************************************************

    //Teacher Queries
    //language=SQl
    protected static final String CREAT_USER_TEACHER =
            "insert into users (first_name, last_name, national_code, dob, entry_date, username, password, user_type)\n" +
                    "values (?,?,?,?,?,?,?,'TEACHER');";
    //language=SQl
    protected static final String UPDATE_USER_TEACHER =
            "update users\n" +
                    "set (first_name, last_name, national_code, dob, entry_date, username, password, user_type) = (?,?,?,?,?,?,?,'TEACHER')\n" +
                    "where id = ?;";
    //language=SQl
    protected static final String CREATE_TEACHER = "insert into teacher  (user_id, course_id) values (?,?);";
    //language=SQl
    protected static final String UPDATE_TEACHER = "update teacher t set (user_id, course_id) = (?,?) where t.user_id = ?;";
    //language=SQL
    protected static final String FIND_TEACHER_BY_ID = "select * from teacher t where t.user_id = ?";
    //language=SQl
    protected static final String DELETE_TEACHER = "delete from teacher t where t.user_id = ?";
    //language=SQl
    protected static final String DELETE_EXAM_FOR_TEACHER = "delete from exam where teacher_id = ?;\n";
    //language=SQL
    protected static final String GET_ALL_TEACHERS = "select * from teacher";
    //language=SQL
    protected static final String GET_TEACHER_USER_DATA =
            "select * from users u inner join teacher t on u.id = t.user_id\n";
    //language=SQL
    protected static final String GET_USER_ID_FROM_TEACHER =
            "select u.id from teacher t inner join users u on t.user_id = u.id where t.user_id = ?;";
    //language=SQl
    protected static final String GET_COURSE_THAT_TEACHER_TEACH =
            "select c.course_title \n" +
                    "from course c\n" +
                    "inner join teacher t\n" +
                    "on t.course_id = c.course_id\n" +
                    "where t.user_id = ? ; ";
    //language=SQL
    protected static final String GET_FULL_NAME_OF_TEACHER_THAT_TAKEN_EXAM =
            "select concat (u.first_name , ' ' , u.last_name) as fullname\n" +
                    "from exam e\n" +
                    "INNER JOIN teacher t on e.teacher_id = t.user_id\n" +
                    "inner join users u on t.user_id = u.id\n" +
                    "where e.exam_id = ?;";
    //***********************************************************************************************


    //Course Queries
    //language=SQl
    protected static final String CREATE_COURSE = "insert into course (course_unit, course_title) values (?,?)";
    //language=SQl
    protected static final String UPDATE_COURSE =
            "update course\n" +
                    "set (course_unit, course_title) = (?,?)\n" +
                    "where course_id = ?";
    //language=SQl
    protected static final String FIND_COURSE_BY_ID = "select * from course where course_id = ?";
    //language=SQl
    protected static final String FIND_EXAM_ID_FOR_DELETE_FROM_STUDENT_EXAM =
            "select e.exam_id\n" +
                    "from exam e\n" +
                    "where e.course_id = ?;";
    //language=SQl
    protected static final String GET_ALL_COURSES = "select * from course";
    //language=SQl
    protected static final String DELETE_STUDENT_EXAM_BECUEASE_EXAM = "delete from student_exam where exam_id = ?;";
    //language=SQl
    protected static final String DELETE_COURSE_FROM_EXAM = "delete from exam where course_id = ?;";
    //language=SQl
    protected static final String DELETE_COURSE_FROM_STUDENT_COURSE = "delete from course_student where course_id = ?;";
    //language=SQl
    protected static final String DELETE_COURSE_FROM_TEACHER = "delete from teacher where course_id = ?;";
    //language=SQl
    protected static final String DELETE_COURSE = "delete from course where course_id = ?;";
    //language=SQl
    protected static final String GET_COURSE_OF_EXAM_BY_EXAM_ID =
            "select c.course_title\n" +
                    "from exam e \n" +
                    "INNER JOIN course c \n" +
                    "ON e.course_id = c.course_id\n" +
                    "where e.exam_id = ?;";


    //***********************************************************************************************


    //Exam Queries
    //language=SQl
    protected static final String CREATE_EXAM =
            "insert into exam (teacher_id , course_id , exam_date)\n" +
                    "values (?,?,?);";
    //language=SQl
    protected static final String UPDATE_EXAM =
            "update exam\n" +
                    "set (teacher_id , course_id, exam_date) = (?,?,?)\n" +
                    "where exam_id = ?;";
    //language=SQl
    protected static final String FIND_EXAM_BY_ID = "select * from exam where exam_id = ?;";
    //language=SQl
    protected static final String DELETE_EXAM = "delete from exam where exam_id = ?;";
    //language=SQl
    protected static final String DELETE_STUDENT_EXAM_FOR_DELETE_EXAM = "delete from student_exam where exam_id = ?;";
    //language=SQl
    protected static final String GET_ALL_EXAMS = "select * from exam;";
    //***********************************************************************************************

    //Manager Queries
    //language=SQl
    protected static final String FIND_MANAGER = "select * from manager where user_id = ?;";
    //***********************************************************************************************

    //User Queries
    //language=SQL
    protected static final String FIND_USER_TYPE_WITH_USERNAME_AND_PASSWORD =
            "select user_type from users where username = ? and password = ?;";
    //language=SQL
    protected static final String FIND_USER_ID_WITH_USERNAME_AND_PASSWORD =
            "select id from users where username = ? and password = ?;";
    //***********************************************************************************************

    //Course Student Queries

    //language=SQL
    protected static final String STUDENT_GET_COURSE =
            "insert into course_student (student_id, course_id)\n" +
                    "values (? , ?);";
    //language=SQL
    protected static final String DELETE_STUDENT_COURSE =
            "delete from course_student where course_id = ?;";
    //language=SQL
    protected static final String GET_ALL_EXAM_OF_STUDENT =
            "select e.* from student_exam se\n" +
                    "inner join student s on se.student_id = s.user_id\n" +
                    "inner join exam e on se.exam_id = e.exam_id\n" +
                    "where s.user_id = ?;";
    //language=SQL
    protected static final String GET_ALL_COURSES_TITLE_OF_STUDENT =
            "select c.course_title from course c\n" +
                    "inner join course_student cs\n" +
                    "on c.course_id = cs.course_id\n" +
                    "inner join student s\n" +
                    "on cs.student_id = s.user_id\n" +
                    "where s.user_id = ?";


    //***********************************************************************************************


}

