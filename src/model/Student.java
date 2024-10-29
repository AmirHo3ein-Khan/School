package model;

import model.enumtype.UserType;

import java.sql.Date;

public class Student extends User{
    private Double gpu;

    public Student(Long id, String firstName, String lastName, Date dob, String nationalCode,
                   Date entryDate, String username, String password, Double gpu) {
        super(id, firstName, lastName, dob, nationalCode, entryDate, username, password);
        this.gpu = gpu;
    }

    public Student() {
    }

    @Override
    public UserType getType() {
        return UserType.STUDENT;
    }

    public Double getGpu() {
        return gpu;
    }

    public void setGpu(Double gpu) {
        this.gpu = gpu;
    }

}
