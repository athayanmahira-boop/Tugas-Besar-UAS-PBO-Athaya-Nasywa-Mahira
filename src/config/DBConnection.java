package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Koneksi database (JDBC)
public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/db_notaris_ppat?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = ""; // isi kalau MySQL root kamu pakai password

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

