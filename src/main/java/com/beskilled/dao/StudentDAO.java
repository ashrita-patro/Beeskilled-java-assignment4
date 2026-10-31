package com.beskilled.dao;

import com.beskilled.model.Student;
import com.beskilled.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    public void addStudent(Student s) throws SQLException {
        String sql = "INSERT INTO students(name,email,course,age) VALUES(?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, s.getName()); p.setString(2, s.getEmail());
            p.setString(3, s.getCourse()); p.setInt(4, s.getAge());
            p.executeUpdate();
        }
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT id,name,email,course,age FROM students ORDER BY id";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) students.add(new Student(r.getInt("id"), r.getString("name"),
                    r.getString("email"), r.getString("course"), r.getInt("age")));
        }
        return students;
    }

    public Student findById(int id) throws SQLException {
        String sql = "SELECT id,name,email,course,age FROM students WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) return new Student(r.getInt("id"), r.getString("name"),
                        r.getString("email"), r.getString("course"), r.getInt("age"));
            }
        }
        return null;
    }

    public boolean updateStudent(Student s) throws SQLException {
        String sql = "UPDATE students SET name=?,email=?,course=?,age=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, s.getName()); p.setString(2, s.getEmail());
            p.setString(3, s.getCourse()); p.setInt(4, s.getAge()); p.setInt(5, s.getId());
            return p.executeUpdate() > 0;
        }
    }

    public boolean deleteStudent(int id) throws SQLException {
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement("DELETE FROM students WHERE id=?")) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        }
    }
}
