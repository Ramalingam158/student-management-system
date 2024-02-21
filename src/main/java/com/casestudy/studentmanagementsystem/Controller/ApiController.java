package com.casestudy.studentmanagementsystem.Controller;

import com.casestudy.studentmanagementsystem.Model.Student;
import com.casestudy.studentmanagementsystem.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiController {

    final StudentService studentService;

    // To save a single student detail
    @PostMapping("/student")
    public String saveSingleStudent(@RequestBody Student student) {
        studentService.saveOrUpdate(student);
        return "OK";
    }

    // To get a single student
    @GetMapping("/student/{id}")
    public Student getSingleStudent(@PathVariable("id") long id) {
        return studentService.getSingleStudent(id);
    }

    // To get all student
    @GetMapping("/student")
    public List<Student> getAllStudents() {
        return studentService.getAllStudent();
    }

    // To update a student based on id
    @PutMapping("/update-student")
    public String updateStudentBasedOnId(@RequestBody Student student) {
        studentService.saveOrUpdate(student);
        return "OK";
    }

    // To import data from CSV to H2 DB
    @PostMapping("/student/import")
    public String importDataFromCSV() {
        studentService.readFromCSV("D:\\Projects\\student-management-system\\data.csv");
        return "OK";
    }

    // To export data to CSV from H2 DB
    @GetMapping("/student/export")
    public String exportDataToCSV() {
        studentService.writeToCSV("D:\\Projects\\student-management-system\\student-management-system\\Export");
        return "OK";
    }
}
