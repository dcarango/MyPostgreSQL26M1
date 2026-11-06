package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBMSConnection {

    private static final String URL_SGBD = "jdbc:postgresql://localhost:5434/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";
    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL_SGBD,USER,PASSWORD);
    }
}
