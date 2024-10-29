package service;

import java.sql.SQLException;

public interface BaseService <T>{
    void creat (T entity);
    void update (T entity);
    void delete (Long id) ;
    int getCount() throws SQLException;
}
