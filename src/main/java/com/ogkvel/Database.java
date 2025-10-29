package com.ogkvel;

import java.sql.*;

public class Database {
    public static Connection getConnection() throws SQLException {
        // Проверяем, работает ли приложение на Render
        String renderEnv = System.getenv("RENDER");
        String databaseUrl = System.getenv("DATABASE_URL");

        if (databaseUrl != null && !databaseUrl.isEmpty()) {
            // Режим Render - используем H2
            System.out.println("Using H2 Database on Render");
            return DriverManager.getConnection("jdbc:h2:file:./crudapp;DB_CLOSE_ON_EXIT=FALSE", "sa", "");
        } else {
            // Локальная разработка - используем MySQL
            System.out.println("Using MySQL for local development");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/crud_app",
                    "root",
                    "Qwas8409547!"
            );
        }
    }

    // Метод для создания таблиц (вызови его в Main.java)
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Проверяем, какая БД используется
            boolean isH2 = System.getenv("DATABASE_URL") != null;

            String createTableSQL;
            if (isH2) {
                // SQL для H2
                createTableSQL = "CREATE TABLE IF NOT EXISTS tasks (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "title VARCHAR(255) NOT NULL, " +
                        "description TEXT, " +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            } else {
                // SQL для MySQL
                createTableSQL = "CREATE TABLE IF NOT EXISTS tasks (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY, " +
                        "title VARCHAR(255) NOT NULL, " +
                        "description TEXT, " +
                        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
            }

            stmt.executeUpdate(createTableSQL);
            System.out.println("Database initialized successfully");

        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }
}