package com.ogkvel.controllers;

import com.ogkvel.Database;
import com.ogkvel.models.Task;
import com.google.gson.Gson;
import spark.Spark;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskController {
    private Gson gson = new Gson();

    public void setupRoutes() {
        Spark.get("/api/tasks", (req, res) -> {
            res.type("application/json");
            try (Connection conn = Database.getConnection()) {
                List<Task> tasks = new ArrayList<>();
                String sql = "SELECT * FROM tasks";
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Task task = new Task();
                    task.setId(rs.getInt("id"));
                    task.setTitle(rs.getString("title"));
                    task.setDescription(rs.getString("description"));
                    task.setDueDate(rs.getDate("due_date"));
                    task.setCompleted(rs.getBoolean("completed"));
                    tasks.add(task);
                }
                return gson.toJson(tasks);
            }
        });

        Spark.get("/api/tasks/:id", (req, res) -> {
            res.type("application/json");
            try (Connection conn = Database.getConnection()) {
                String sql = "SELECT * FROM tasks WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(req.params("id")));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    Task task = new Task();
                    task.setId(rs.getInt("id"));
                    task.setTitle(rs.getString("title"));
                    task.setDescription(rs.getString("description"));
                    task.setDueDate(rs.getDate("due_date"));
                    task.setCompleted(rs.getBoolean("completed"));
                    return gson.toJson(task);
                } else {
                    res.status(404);
                    return "{\"error\": \"Task not found\"}";
                }
            }
        });

        Spark.post("/api/tasks", (req, res) -> {
            res.type("application/json");
            try (Connection conn = Database.getConnection()) {
                Task task = gson.fromJson(req.body(), Task.class);
                String sql = "INSERT INTO tasks (title, description, due_date, completed) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, task.getTitle());
                stmt.setString(2, task.getDescription());
                stmt.setDate(3, new java.sql.Date(task.getDueDate().getTime()));
                stmt.setBoolean(4, task.isCompleted());
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    task.setId(rs.getInt(1));
                }
                res.status(201);
                return gson.toJson(task);
            }
        });

        Spark.put("/api/tasks/:id", (req, res) -> {
            res.type("application/json");
            try (Connection conn = Database.getConnection()) {
                Task task = gson.fromJson(req.body(), Task.class);
                String sql = "UPDATE tasks SET title = ?, description = ?, due_date = ?, completed = ? WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, task.getTitle());
                stmt.setString(2, task.getDescription());
                stmt.setDate(3, new java.sql.Date(task.getDueDate().getTime()));
                stmt.setBoolean(4, task.isCompleted());
                stmt.setInt(5, Integer.parseInt(req.params("id")));

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    res.status(404);
                    return "{\"error\": \"Task not found\"}";
                }
                return gson.toJson(task);
            }
        });

        Spark.delete("/api/tasks/:id", (req, res) -> {
            try (Connection conn = Database.getConnection()) {
                String sql = "DELETE FROM tasks WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(req.params("id")));

                int affectedRows = stmt.executeUpdate();
                if (affectedRows == 0) {
                    res.status(404);
                    return "{\"error\": \"Task not found\"}";
                }
                res.status(204);
                return "";
            }
        });
    }
}