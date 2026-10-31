package com.beskilled.controller;

import com.beskilled.dao.StudentDAO;
import com.beskilled.model.Student;
import java.sql.SQLException;
import java.util.List;

// Controller layer: connects the menu/view with the data-access layer.
public class StudentController {
    private final StudentDAO dao = new StudentDAO();
    public void add(Student student) throws SQLException { dao.addStudent(student); }
    public List<Student> all() throws SQLException { return dao.getAllStudents(); }
    public Student find(int id) throws SQLException { return dao.findById(id); }
    public boolean update(Student student) throws SQLException { return dao.updateStudent(student); }
    public boolean delete(int id) throws SQLException { return dao.deleteStudent(id); }
}
