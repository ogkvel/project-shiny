CREATE DATABASE IF NOT EXISTS crud_app;
USE crud_app;

CREATE TABLE IF NOT EXISTS tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    due_date DATE,
    completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Пример тестовых данных
INSERT INTO tasks (title, description, due_date, completed) VALUES
('Learn Java', 'Study Java programming', '2024-12-31', false),
('Build CRUD app', 'Create simple CRUD application', '2024-11-30', true);