# Project Shiny -  aplikacja Task Manager CRUD

Aplikacja CRUD w Java z bazą danych MySQL i interfejsem webowym.

## Funkcjonalności
- Tworzenie, odczyt, aktualizacja, usuwanie zadań
- Integracja z bazą danych MySQL
- REST API
- Interfejs webowy

## Technologie
- Java 11
- Spark Java Framework
- MySQL
- Maven

## Szybki start
1. Sklonuj repozytorium: `git clone https://github.com/ogkvel/project-shiny.git`
2. Zaimportuj w IntelliJ jako projekt Maven
3. Skonfiguruj bazę MySQL (uruchom create_table.sql)
4. Zaktualizuj Database.java swoimi danymi MySQL
5. Uruchom Main.java
6. Otwórz http://localhost:4567

## Endpointy API
- GET `/api/tasks` - Pobierz wszystkie zadania
- POST `/api/tasks` - Utwórz nowe zadanie
- PUT `/api/tasks/{id}` - Zaktualizuj zadanie
- DELETE `/api/tasks/{id}` - Usuń zadanie
