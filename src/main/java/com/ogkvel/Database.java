package com.ogkvel;

import java.sql.*;

public class Database {
    // Если использовали стандартные настройки:
    private static final String URL = "jdbc:mysql://localhost:3306/crud_app";
    private static final String USER = "root";
    private static final String PASSWORD = "Qwas8409547!";



    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}