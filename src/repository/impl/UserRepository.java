package repository.impl;

import database.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private final Database database = new Database();
    public String findUserTypeWithUsernameAndPassword(String username , String password)throws SQLException {
        PreparedStatement pr = this.database.getPreparedStatement(Constant.FIND_USER_TYPE_WITH_USERNAME_AND_PASSWORD);
        pr.setString(1 , username);
        pr.setString(2, password);
        ResultSet resultSet = pr.executeQuery();
        String userType = "";
        while (resultSet.next()){
            userType = resultSet.getString("user_type");
        }
        return userType;
    }
    public Long findUserIdWithUsernameAndPassword(String username , String password) throws SQLException {
        PreparedStatement pr = this.database.getPreparedStatement(Constant.FIND_USER_ID_WITH_USERNAME_AND_PASSWORD);
        pr.setString(1 , username);
        pr.setString(2, password);
        ResultSet resultSet = pr.executeQuery();
        Long id = 0L;
        while (resultSet.next()){
            id = resultSet.getLong("id");
        }
        return id;
    }
}
