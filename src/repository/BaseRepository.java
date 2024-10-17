package repository;

import exception.NotFoundException;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;

public interface BaseRepository <T>{
    void creat (T entity) throws SQLException;
    void update (T entity) throws SQLException , NotFoundException;
    Optional<T> findById (Long id) throws SQLException , NotFoundException;
    void delete (Long id) throws SQLException , NotFoundException;
    Set<T> getAll() throws SQLException;
    int getCount() throws SQLException;
}
