package database;

import java.sql.*;

public class Database {
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/schoolmanagementsystem";
    private static final String DATABASE_USERNAME = "postgres";
    private static final String DATABASE_PASSWORD = "154282";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
    }

    public Statement getStatement() throws SQLException {
        return this.getConnection().createStatement();
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return this.getConnection().prepareStatement(sql);
    }

    public PreparedStatement getPreparedStatementAndColumnIndexes(String sql, int columnIndexes) throws SQLException {
        return this.getConnection().prepareStatement(sql, columnIndexes);
    }
}
