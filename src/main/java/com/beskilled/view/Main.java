package com.beskilled.view;

import com.beskilled.controller.StudentController;
import com.beskilled.model.Student;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner input = new Scanner(System.in);
    private static final StudentController controller = new StudentController();

    public static void main(String[] args) {
        System.out.println("==================================");
        System.out.println("   STUDENT MANAGEMENT SYSTEM");
        System.out.println("==================================");
        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt("Choose an option: ");
            try {
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> viewStudents();
                    case 3 -> searchStudent();
                    case 4 -> updateStudent();
                    case 5 -> deleteStudent();
                    case 6 -> exportCsv();
                    case 0 -> { running = false; System.out.println("Thank you!"); }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
                System.out.println("Check MySQL is running and DBConnection.java has the correct password.");
            } catch (IOException e) {
                System.out.println("File error: " + e.getMessage());
            }
        }
        input.close();
    }

    private static void showMenu() {
        System.out.println("\n1. Add student");
        System.out.println("2. View all students");
        System.out.println("3. Search student by ID");
        System.out.println("4. Update student");
        System.out.println("5. Delete student");
        System.out.println("6. Export students to CSV file");
        System.out.println("0. Exit");
    }

    private static void addStudent() throws SQLException {
        String name = readText("Name: ");
        String email = readText("Email: ");
        String course = readText("Course: ");
        int age = readInt("Age: ");
        if (age < 1 || age > 120) { System.out.println("Enter a valid age."); return; }
        controller.add(new Student(name, email, course, age));
        System.out.println("Student added successfully.");
    }

    private static void viewStudents() throws SQLException {
        List<Student> students = controller.all();
        if (students.isEmpty()) { System.out.println("No student records found."); return; }
        students.forEach(System.out::println);
    }

    private static void searchStudent() throws SQLException {
        int id = readInt("Student ID: ");
        Student s = controller.find(id);
        System.out.println(s == null ? "Student not found." : s);
    }

    private static void updateStudent() throws SQLException {
        int id = readInt("ID of student to update: ");
        Student existing = controller.find(id);
        if (existing == null) { System.out.println("Student not found."); return; }
        System.out.println("Enter the new details:");
        String name = readText("Name: ");
        String email = readText("Email: ");
        String course = readText("Course: ");
        int age = readInt("Age: ");
        if (age < 1 || age > 120) { System.out.println("Enter a valid age."); return; }
        boolean updated = controller.update(new Student(id, name, email, course, age));
        System.out.println(updated ? "Student updated successfully." : "Update failed.");
    }

    private static void deleteStudent() throws SQLException {
        int id = readInt("ID of student to delete: ");
        System.out.print("Are you sure? Type YES to confirm: ");
        String confirm = input.nextLine().trim();
        if (!confirm.equals("YES")) { System.out.println("Delete cancelled."); return; }
        System.out.println(controller.delete(id) ? "Student deleted." : "Student not found.");
    }

    private static void exportCsv() throws SQLException, IOException {
        List<Student> students = controller.all();
        try (FileWriter writer = new FileWriter("students_export.csv")) {
            writer.write("id,name,email,course,age\n");
            for (Student s : students) {
                writer.write(s.getId() + ",\"" + csv(s.getName()) + "\",\"" + csv(s.getEmail())
                        + "\",\"" + csv(s.getCourse()) + "\"," + s.getAge() + "\n");
            }
        }
        System.out.println("Export complete: students_export.csv (project folder).");
    }

    private static String csv(String value) { return value.replace("\"", "\"\""); }
    private static String readText(String prompt) {
        System.out.print(prompt);
        return input.nextLine().trim();
    }
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try { return Integer.parseInt(input.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Please enter a whole number."); }
        }
    }
}
