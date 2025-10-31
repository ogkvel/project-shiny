package com.ogkvel;

import java.sql.*;

public class Database {
    // Используем константы для ключей переменных окружения
    private static final String DB_URL_ENV = "DB_URL";
    private static final String DB_USER_ENV = "DB_USER";
    private static final String DB_PASSWORD_ENV = "DB_PASSWORD";

    // Схема таблицы tasks, унифицированная с create_table.sql
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS tasks (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "title VARCHAR(255) NOT NULL, " +
            "description TEXT, " +
            "due_date DATE, " +
            "completed BOOLEAN DEFAULT FALSE, " +
            "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";


    public static Connection getConnection() throws SQLException {
        // 1. Пытаемся подключиться, используя переменные окружения (для Render/Production)
        String dbUrl = System.getenv(DB_URL_ENV);
        String dbUser = System.getenv(DB_USER_ENV);
        String dbPassword = System.getenv(DB_PASSWORD_ENV);

        if (dbUrl != null && !dbUrl.isEmpty()) {
            // Режим Production - используем переменные окружения для удаленной БД
            System.out.println("Using Production Database (via Env Variables).");
            // Проверяем, что все переменные установлены
            if (dbUser == null || dbPassword == null) {
                throw new SQLException("Missing database credentials (USER or PASSWORD) in environment variables.");
            }
            // Установка драйвера MySQL (если он не установлен автоматически)
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.err.println("MySQL Driver not found.");
                throw new SQLException("MySQL JDBC Driver not found in classpath.", e);
            }

            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } else {
            // 2. Режим Local Development - используем жестко заданные локальные данные
            System.out.println("Using MySQL for local development.");
            // Убедитесь, что ваша локальная БД запущена на порту 3306
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/crud_app",
                    "root",
                    "Qwas8409547!"
            );
        }
    }

    // Метод для создания таблиц
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Используем унифицированную схему
            stmt.executeUpdate(CREATE_TABLE_SQL);
            System.out.println("Database initialized successfully on connected DB.");

        } catch (SQLException e) {
            System.err.println("CRITICAL ERROR: Failed to initialize database.");
            e.printStackTrace();
        }
    }
}
