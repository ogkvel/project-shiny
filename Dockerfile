# Используем официальный образ Maven с Java 11 для сборки
FROM maven:3.8.1-openjdk-11 as builder

# Устанавливаем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем все файлы проекта в контейнер
COPY . .

# Собираем приложение (пропускаем тесты для скорости)
RUN mvn clean package -DskipTests

# Второй этап - создаем легкий образ для запуска
FROM openjdk:11-jre-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный JAR из первого этапа
COPY --from=builder /app/target/project-shimer-1.0-SNAPSHOT.jar app.jar

# Открываем порт, который использует Render
EXPOSE 10000

# Команда для запуска приложения
CMD ["java", "-jar", "app.jar"]