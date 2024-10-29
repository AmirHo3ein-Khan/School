package model;
import model.enumtype.UserType;
import java.sql.Date;

public class Teacher extends User{
    private Long courseId;

    public Teacher(Long id, String firstName, String lastName, Date dob, String nationalCode,
                   Date entryDate, String username, String password, Long courseId) {
        super(id, firstName, lastName, dob, nationalCode, entryDate, username, password);
        this.courseId = courseId;
    }

    public Teacher() {
    }

    @Override
    public UserType getType() {
        return UserType.TEACHER;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

}
