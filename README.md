# Task Management Application

## 📜 Описание
CRUD-приложение для управления задачами. Реализовано на основе **Spring Boot** с использованием RESTful API и AOP для логирования.

---

## 🚀 Технологии и зависимости
Проект использует следующие зависимости и версии:

- **Java**: 17
- **Spring Boot**: 3.4.4
- **Spring Boot Starter Data JPA**: Для работы с базой данных.
- **Spring Boot Starter Web**: Для создания RESTful API.
- **Postgresql**: База данных.
- **Lombok**: Для сокращения шаблонного кода.
- **Spring Boot Starter Test**: Для написания и выполнения тестов.

---

## 📋 API Эндпоинты

### Задачи:
- **POST /tasks**  
  Создать новую задачу.

- **GET /tasks/{id}**  
  Получить задачу по ID.

- **PUT /tasks/{id}**  
  Обновить задачу по ID.

- **DELETE /tasks/{id}**  
  Удалить задачу по ID.

- **GET /tasks**  
  Получить список всех задач.

---

## 🛠 Использование AOP
В проекте используется аспектно-ориентированное программирование (AOP) для логирования:

- **Before**  
  Логирование перед выполнением метода.

- **AfterReturning**  
  Логирование после успешного выполнения метода.

- **AfterThrowing**  
  Логирование исключений.

- **Around**  
  Замер времени выполнения метода.

---
## 🛠 Запуск приложения 

git clone https://github.com/alexonlyman/task_management_hw_t1

cd task_management_hw_t1

🛠 Шаг 2: Сборка JAR-файла

mvn clean package -DskipTests

🐳 Шаг 3: Запуск с помощью Docker Compose

docker compose build

docker compose up -d


