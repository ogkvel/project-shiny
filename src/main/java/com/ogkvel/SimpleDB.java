package com.ogkvel;

import java.sql.*;

public class SimpleDB {
    static {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("✅ PostgreSQL Driver loaded");
        } catch (Exception e) {
            System.out.println("❌ Driver error: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://dpg-d448d4muk2gs73a0ihbg-a.frankfurt-postgres.render.com/crud_app_ljup?ssl=true&sslmode=require";
        String user = "shiny_user";
        String password = "Sm6Bwjyk52ubjz3WceEwIrmGV1LvVdxk";

        System.out.println("🔗 Connecting to: " + url);
        return DriverManager.getConnection(url, user, password);
    }

    public static void init() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS tasks (" +
                    "id SERIAL PRIMARY KEY, " +
                    "title VARCHAR(255), " +
                    "description TEXT)";

            stmt.executeUpdate(sql);
            System.out.println("✅ Table created");

        } catch (Exception e) {
            System.out.println("❌ Init error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}