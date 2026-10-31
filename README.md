# BeSkillled Week 4 Capstone — Student Management System

A beginner-friendly Java console application demonstrating OOP, MVC-style separation, collections, JDBC/MySQL, CRUD operations, and CSV file handling.

## Features
- Add, view, search, update, and delete student records
- Stores records persistently in MySQL
- Exports all student records to `students_export.csv`
- Uses `PreparedStatement` for database queries
- Separates model, DAO, controller, view, and database connection code

## Requirements
- JDK 17 or newer
- MySQL Server and MySQL Workbench (or another MySQL client)
- Maven
- VS Code with Java Extension Pack

## Setup
1. Start MySQL Server.
2. Open `database/schema.sql` in MySQL Workbench and execute it. This creates the `student_management` database and `students` table.
3. Open `src/main/java/com/beskilled/util/DBConnection.java`.
4. Replace `YOUR_MYSQL_PASSWORD` with your local MySQL password. Never commit a real password to a public repository; use a local-only configuration for a public submission.
5. Open this project folder in VS Code. Allow Maven to download dependencies.
6. Run `com.beskilled.view.Main` from VS Code, or use a terminal:
   - Windows: `mvn clean compile`
   - Then run `Main` from VS Code's Java Run button. If using terminal only, use a Java runner/IDE with Maven classpath support.

## Test checklist
- Add a student with a unique email
- View all students
- Search for an existing and a non-existing ID
- Update a student
- Delete a student (confirm with `YES`)
- Export records and open `students_export.csv`
- Restart the app and confirm remaining records are still in MySQL

## Project structure
```text
student-management-system/
├── database/schema.sql
├── src/main/java/com/beskilled/
│   ├── model/Student.java
│   ├── dao/StudentDAO.java
│   ├── controller/StudentController.java
│   ├── util/DBConnection.java
│   └── view/Main.java
├── pom.xml
└── README.md
```

## Notes
- The email column is unique; use a different email for each student.
- The database must be running before the application starts.
- This is an educational capstone. Validate inputs and keep credentials private before sharing publicly.
