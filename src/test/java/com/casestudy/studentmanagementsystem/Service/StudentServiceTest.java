package com.casestudy.studentmanagementsystem.Service;

import com.casestudy.studentmanagementsystem.Model.Student;
import com.casestudy.studentmanagementsystem.Repository.StudentRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @InjectMocks
    StudentService studentService;

    @Mock
    StudentRepo studentRepoMock;
    Student student = new Student(100, "Ram", "ram@mail.com");

    @Test
    void saveOrUpdateTest() {
        when(studentRepoMock.save(student)).thenReturn(student);
        String actualResult = studentService.saveOrUpdate(student);
        Assertions.assertEquals("OK", actualResult);
    }

    @Test
    void getSingleStudentTest_StudentExists() {
        when(studentRepoMock.findById(100L)).thenReturn(Optional.ofNullable(student));

        Optional<Student> actualResult = studentService.getSingleStudent(100);

        Assertions.assertEquals(student.getStudentId(), actualResult.get().getStudentId());
        Assertions.assertEquals(student.getName(), actualResult.get().getName());
    }

    @Test
    void getSingleStudentTest_NoStudentExists() {
        when(studentRepoMock.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Student> actualResult = studentRepoMock.findById(1001L);
        Assertions.assertFalse(actualResult.isPresent());
    }

    @Test
    void getAllStudentTest_StudentsExist() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);

        when(studentRepoMock.findAll()).thenReturn(studentList);
        List<Student> actualResult = studentService.getAllStudent();

        Assertions.assertEquals(1, actualResult.size());
        Assertions.assertEquals(student.getStudentId(), actualResult.get(0).getStudentId());
    }

    @Test
    void getAllStudentTest_NoStudentsExist() {
        List<Student> studentList = new ArrayList<>();

        when(studentRepoMock.findAll()).thenReturn(studentList);
        List<Student> actualResult = studentService.getAllStudent();

        Assertions.assertEquals(0, actualResult.size());
    }

    @Test
    void readFromCSVTest_Success() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);

        when(studentRepoMock.saveAll(any())).thenReturn(studentList);
        String actualResult = studentService.readFromCSV("D:\\Projects\\student-management-system\\data.csv");
        Assertions.assertEquals("OK", actualResult);
    }

    @Test
    void readFromCSVTest_WrongPath() {
        String actualResult = studentService.readFromCSV("D:\\Projects\\student-management\\data.csv");
        Assertions.assertEquals("IO Exception", actualResult);
    }

    @Test
    void writeToCSVTest_Success() {
        String path = "D:\\Projects\\student-management-system\\student-management-system\\Export";
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);

        when(studentRepoMock.findAll()).thenReturn(studentList);

        String actualResult = studentService.writeToCSV(path);

        Assertions.assertEquals("OK", actualResult);
    }

    @Test
    void writeToCSVTest_WrongPath() {
        String path = "D:\\Projects\\student-management-system\\student-management-system\\Exp";
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);

        when(studentRepoMock.findAll()).thenReturn(studentList);

        String actualResult = studentService.writeToCSV(path);

        Assertions.assertEquals("IO Exception", actualResult);
    }
}
