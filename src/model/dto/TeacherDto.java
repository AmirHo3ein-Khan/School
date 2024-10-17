package model.dto;

import java.sql.Date;

public class TeacherDto {
    private Long teacherId;
    private String courseTitle;
    private String firstName;
    private String lastName;
    private String nationalCode;
    private Date dob;
    private Date entryDate;

    public TeacherDto(Long teacherId, String courseTitle, String firstName,
                      String lastName, String nationalCode, Date dob, Date entryDate) {
        this.teacherId = teacherId;
        this.courseTitle = courseTitle;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.dob = dob;
        this.entryDate = entryDate;
    }

    public TeacherDto() {
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    @Override
    public String toString() {
        return String.format("%-5d |%-12s | %-12s | %-15s | %-12s | %-12s | %-12s\n",
                teacherId, firstName, lastName, nationalCode, dob, entryDate, courseTitle);
    }
}
