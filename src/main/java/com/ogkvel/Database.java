package com.ogkvel;

import java.sql.*;

public class Database {
    static {
        try {
            // Загружаем PostgreSQL драйвер
            Class.forName("org.postgresql.Driver");
            System.out.println("PostgreSQL Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL Driver error: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        // Проверяем переменную окружения (DB_URL вместо DATABASE_URL)
        String databaseUrl = System.getenv("DB_URL");

        if (databaseUrl != null && !databaseUrl.isEmpty()) {
            // Режим Render - используем PostgreSQL
            System.out.println("Using PostgreSQL on Render");
            return DriverManager.getConnection(databaseUrl);
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

            // SQL для PostgreSQL (SERIAL вместо AUTO_INCREMENT)
            String createTableSQL = "CREATE TABLE IF NOT EXISTS tasks (" +
                    "id SERIAL PRIMARY KEY, " +
                    "title VARCHAR(255), " +
                    "description TEXT, " +
                    "created_at TIMESTAMP DEFAULT NOW())";

            stmt.executeUpdate(createTableSQL);
            System.out.println("PostgreSQL table created successfully");

        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
            e.printStackTrace();
        }
    }
}