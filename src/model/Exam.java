package model;

import java.sql.Date;

public class Exam extends BaseModel{
    private Date date;
    private Long teacherId;
    private Long courseId;

    public Exam(Long id, Date date, Long teacherId, Long courseId) {
        super(id);
        this.date = date;
        this.teacherId = teacherId;
        this.courseId = courseId;
    }

    public Exam() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
