package model.dto;

import java.sql.Date;

public class StudentDto {
    private Long studentId;
    private String firstName;
    private String lastName;
    private String nationalCode;
    private Date dob;
    private Date entryDate;
    private double gpu;

    public StudentDto(Long studentId, String firstName, String lastName,
                      String nationalCode, Date dob, Date entryDate, double gpu) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.dob = dob;
        this.entryDate = entryDate;
        this.gpu = gpu;
    }

    public StudentDto() {
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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

    public double getGpu() {
        return gpu;
    }

    public void setGpu(double gpu) {
        this.gpu = gpu;
    }

    @Override
    public String toString() {
        return String.format("%-5d | %-12s | %-12s | %-14s | %-12s | %-12s | %-12s\n",
                studentId, firstName , lastName , nationalCode , dob , entryDate, gpu);
    }
}
