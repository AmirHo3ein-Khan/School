package model;

import model.enumtype.UserType;

import java.sql.Date;

public class Manager extends User{
    public Manager(Long id, String firstName, String lastName, Date dob,
                   String nationalCode, Date entryDate, String username, String password) {
        super(id, firstName, lastName, dob, nationalCode, entryDate, username, password);
    }

    public Manager() {
    }

    @Override
    public UserType getType() {
        return UserType.MANAGER;
    }
}
