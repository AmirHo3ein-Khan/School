package repository;

import exception.NotFoundException;
import model.Manager;
import java.sql.SQLException;
import java.util.Optional;

public interface ManagerRepository {
    Optional<Manager> findById (Long id) throws SQLException, NotFoundException;
}
