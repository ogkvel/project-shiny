package com.ogkvel;

import java.sql.*;

public class Database {
    static {
        // Загружаем только H2 драйвер для Render
        try {
            Class.forName("org.h2.Driver");
            System.out.println("H2 Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("H2 Driver not found: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        // Проверяем, работает ли приложение на Render
        String databaseUrl = System.getenv("DATABASE_URL");

        if (databaseUrl != null && !databaseUrl.isEmpty()) {
            // Режим Render - используем H2 in-memory
            System.out.println("Using H2 Database on Render");
            return DriverManager.getConnection("jdbc:h2:mem:crudapp;DB_CLOSE_DELAY=-1", "sa", "");
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

    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Упрощенный SQL для H2
            String createTableSQL = "CREATE TABLE IF NOT EXISTS tasks (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "title VARCHAR(255), " +
                    "description VARCHAR(1000), " +
                    "created_at TIMESTAMP DEFAULT NOW())";

            stmt.executeUpdate(createTableSQL);
            System.out.println("Database table created successfully");

        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
            e.printStackTrace();
        }
    }
}