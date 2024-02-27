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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {
    @InjectMocks
    StudentService studentService;

    @Mock
    StudentRepo studentRepoMock;
    private final Student student1 = new Student(100, "Ram", "ram@mail.com");
    private final Student student2 = new Student(101, "Sam", "sam@mail.com");

    @Test
    void saveOrUpdateTest() {
        when(studentRepoMock.save(student1)).thenReturn(student1);
        String actualResult = studentService.saveOrUpdate(student1);
        Assertions.assertEquals("OK", actualResult);
    }

    @Test
    void getSingleStudentTest_StudentExists() {
        when(studentRepoMock.findById(100L)).thenReturn(Optional.of(student1));
        Optional<Student> actualResult = studentService.getSingleStudent(100);
        assertThat(student1.getStudentId()).isEqualTo(actualResult.get().getStudentId());
    }

    @Test
    void getSingleStudentTest_NoStudentExists() {
        when(studentRepoMock.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Student> actualResult = studentRepoMock.findById(1001L);
        assertThat(actualResult.isPresent()).isFalse();
    }

    @Test
    void getAllStudentTest_StudentsExist() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        when(studentRepoMock.findAll()).thenReturn(studentList);
        List<Student> actualResult = studentService.getAllStudent();

        assertThat(actualResult.size()).isEqualTo(studentList.size());
        assertThat(actualResult.get(0).getStudentId()).isEqualTo(student1.getStudentId());
    }

    @Test
    void getAllStudentTest_NoStudentsExist() {
        List<Student> studentList = new ArrayList<>();

        when(studentRepoMock.findAll()).thenReturn(studentList);
        List<Student> actualResult = studentService.getAllStudent();

        assertThat(actualResult.size()).isEqualTo(0);
    }

    @Test
    void readFromCSVTest_Success() {
        String filePath = "CSV\\data.csv";
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        when(studentRepoMock.saveAll(any())).thenReturn(studentList);
        String actualResult = studentService.readFromCSV(filePath);

        assertThat(actualResult).isEqualTo("OK");
    }

    @Test
    void readFromCSVTest_WrongPath() {
        String filePath = "CS\\data.csv";
        String actualResult = studentService.readFromCSV(filePath);

        assertThat(actualResult).isEqualTo(filePath + " (The system cannot find the path specified)");
    }

    @Test
    void writeToCSVTest_Success() {
        String path = "CSV\\export.csv";
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);

        when(studentRepoMock.findAll()).thenReturn(studentList);
        String actualResult = studentService.writeToCSV(path);

        assertThat(actualResult).isEqualTo("OK");
    }

    @Test
    void writeToCSVTest_WrongPath() {
        String filePath = "CS\\export.csv";
        List<Student> studentList = new ArrayList<>();
        studentList.add(student1);

        when(studentRepoMock.findAll()).thenReturn(studentList);
        String actualResult = studentService.writeToCSV(filePath);

        assertThat(actualResult).isEqualTo(filePath + " (The system cannot find the path specified)");
    }
}
