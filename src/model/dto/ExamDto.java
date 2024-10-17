package model.dto;

import java.sql.Date;

public class ExamDto {
    private Long examId;
    private String teacherFullName;
    private String courseTitle;
    private Date examDate;

    public ExamDto(Long examId, String teacherFullName, String courseTitle, Date examDate) {
        this.examId = examId;
        this.teacherFullName = teacherFullName;
        this.courseTitle = courseTitle;
        this.examDate = examDate;
    }

    public ExamDto() {
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public String getTeacherFullName() {
        return teacherFullName;
    }

    public void setTeacherFullName(String teacherFullName) {
        this.teacherFullName = teacherFullName;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    @Override
    public String toString() {
        return String.format("%-5d | %-21s | %-15s | %-12s ",examId, teacherFullName, courseTitle, examDate);
    }
}
