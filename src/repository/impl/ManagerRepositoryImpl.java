package repository.impl;

import database.Database;
import exception.NotFoundException;
import model.Manager;
import repository.ManagerRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ManagerRepositoryImpl implements ManagerRepository {
    private final Database database = new Database();
    @Override
    public Optional<Manager> findById(Long id) throws SQLException, NotFoundException {
        PreparedStatement pr = this.database.getPreparedStatement(Constant.FIND_MANAGER);
        pr.setLong(1,id);
        ResultSet managerResultSet = pr.executeQuery();
        pr = database.getPreparedStatement(Constant.GET_USER_DATA_FOR_FIND);
        pr.setLong(1,id);
        ResultSet userResultSet = pr.executeQuery();
        Optional<Manager> optional = Optional.empty();
        while (managerResultSet.next() && userResultSet.next()) {
            Manager manager = new Manager();
            manager.setId(managerResultSet.getLong("user_id"));
            manager.setFirstName(userResultSet.getString("first_name"));
            manager.setLastName(userResultSet.getString("last_name"));
            manager.setDob(userResultSet.getDate("dob"));
            manager.setNationalCode(userResultSet.getString("national_code"));
            manager.setEntryDate(userResultSet.getDate("entry_date"));
            manager.setUsername(userResultSet.getString("username"));
            manager.setPassword(userResultSet.getString("password"));
            optional = Optional.of(manager);
        }
        return optional;
    }
}
