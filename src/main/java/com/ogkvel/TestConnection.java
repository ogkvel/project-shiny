package com.ogkvel;

import java.sql.*;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection conn = Database.getConnection();
            System.out.println("✅ MySQL podlaczone!");

            // Проверим таблицу
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM tasks");
            if (rs.next()) {
                System.out.println("✅ tabelka tasks istnieje, zapisow: " + rs.getInt(1));
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("❌ Blad polaczenia: " + e.getMessage());
        }
    }
}