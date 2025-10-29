package com.ogkvel;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        // Автоматическое определение порта для Render
        port(getAssignedPort());

        // Статические файлы
        staticFiles.location("/public");

        // CORS headers
        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
        });

        // Инициализация базы данных
        Database.initializeDatabase();

        // Настройка маршрутов
        com.ogkvel.controllers.TaskController taskController = new com.ogkvel.controllers.TaskController();
        taskController.setupRoutes();

        // Дополнительные endpoint'ы для проверки
        get("/", (req, res) -> {
            return "CRUD Application is running! Use /api/tasks endpoints";
        });

        get("/health", (req, res) -> {
            try {
                Database.getConnection().close();
                return "✅ Application is healthy - Database connection OK";
            } catch (Exception e) {
                return "❌ Application error - " + e.getMessage();
            }
        });

        System.out.println("Server running on port: " + getAssignedPort());
    }

    // Метод для определения порта (Render или локальный)
    static int getAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            int renderPort = Integer.parseInt(processBuilder.environment().get("PORT"));
            System.out.println("Using Render port: " + renderPort);
            return renderPort;
        }
        System.out.println("Using default port: 4567");
        return 4567; // default port for local development
    }
}