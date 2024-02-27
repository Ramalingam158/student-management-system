package com.casestudy.studentmanagementsystem.Controller;

import com.casestudy.studentmanagementsystem.Model.Student;
import com.casestudy.studentmanagementsystem.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final StudentService studentService;

    // To save a single student detail
    @PostMapping("/student")
    public String saveSingleStudent(@RequestBody Student student) {
        return studentService.saveOrUpdate(student);
    }

    // To get a single student
    @GetMapping("/student/{id}")
    public Student getSingleStudent(@PathVariable("id") long id) {
        Optional<Student> result = studentService.getSingleStudent(id);

        if(result.isPresent())
            return result.get();
        else
            throw new NoSuchElementException();
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
        return studentService.readFromCSV("CSV\\data.csv");
    }

    // To export data to CSV from H2 DB
    @GetMapping("/student/export")
    public String exportDataToCSV() {
        return studentService.writeToCSV("CSV\\export.csv");
    }
}
