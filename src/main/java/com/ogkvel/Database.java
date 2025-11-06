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
        String databaseUrl = System.getenv("DB_URL");

        if (databaseUrl != null && !databaseUrl.isEmpty()) {
            System.out.println("Using PostgreSQL on Render");
            String username = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");

            System.out.println("Connecting to: " + databaseUrl);
            System.out.println("Username: " + username);

            // Добавляем sslmode=disable в URL
            String urlWithSSL = databaseUrl + "?sslmode=disable";
            return DriverManager.getConnection(urlWithSSL, username, password);
        } else {
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

            String createTableSQL = "CREATE TABLE IF NOT EXISTS tasks (" +
                    "id SERIAL PRIMARY KEY, " +
                    "title VARCHAR(255), " +
                    "description TEXT, " +
                    "due_date DATE, " +
                    "completed BOOLEAN DEFAULT FALSE, " +
                    "created_at TIMESTAMP DEFAULT NOW())";

            stmt.executeUpdate(createTableSQL);
            System.out.println("PostgreSQL table created successfully");

        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
            e.printStackTrace();
        }
    }
}