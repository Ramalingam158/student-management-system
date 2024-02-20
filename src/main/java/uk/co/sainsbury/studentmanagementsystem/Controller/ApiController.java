package uk.co.sainsbury.studentmanagementsystem.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.sainsbury.studentmanagementsystem.Model.Student;
import uk.co.sainsbury.studentmanagementsystem.Service.StudentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiController {

    final StudentService studentService;

    // To save a single student detail
    @RequestMapping(method = RequestMethod.POST, path = "/student")
    public ResponseEntity<String> saveSingleStudent(@RequestBody Student student) {
        studentService.saveOrUpdate(student);
        return ResponseEntity.ok().body("OK");
    }

    // To get a single student
    @RequestMapping(method = RequestMethod.GET, path = "/student/{id}")
    public ResponseEntity<Student> getSingleStudent(@PathVariable("id") long id) {
        Student student = studentService.getSingleStudent(id);

        return ResponseEntity.ok().body(student);
    }

    // To get all student
    @RequestMapping(method = RequestMethod.GET, path = "/student")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> studentList = studentService.getAllStudent();

        return ResponseEntity.ok().body(studentList);
    }

    // To update a student based on id
    @RequestMapping(method = RequestMethod.PUT, path = "/update-student")
    public ResponseEntity<String> updateStudentBasedOnId(Student student) {
        studentService.saveOrUpdate(student);

        return ResponseEntity.ok().body("OK");
    }

    // To import data from CSV to H2 DB
    @RequestMapping(method = RequestMethod.POST, path = "student/import")
    public ResponseEntity<String> importDataFromCSV() {
        studentService.readFromCSV();
        return ResponseEntity.ok().body("OK");
    }

    // To export data to CSV from H2 DB
    @RequestMapping(method = RequestMethod.GET, path = "/student/export")
    public ResponseEntity<String> exportDataToCSV() {
        studentService.writeToCSV();
        return ResponseEntity.ok().body("OK");
    }
}
