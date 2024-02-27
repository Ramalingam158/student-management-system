package com.casestudy.studentmanagementsystem.Controller;

import com.casestudy.studentmanagementsystem.Service.StudentService;
import com.casestudy.studentmanagementsystem.Model.Student;
import jakarta.servlet.ServletException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ApiControllerTest {
    @InjectMocks
    ApiController apiController;

    @Mock
    StudentService studentServiceMock;

    private final Student student = new Student(100, "Ram", "ram@mail.com");

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
    }

    @Test
    void saveSingleStudentTest() throws Exception {
        JSONObject input = new JSONObject();
        input.put("id", student.getStudentId());
        input.put("name", student.getName());
        input.put("email", student.getEmail());

        when(studentServiceMock.saveOrUpdate(any())).thenReturn("OK");

        MvcResult actualResult = mockMvc.perform(MockMvcRequestBuilders.post("/student")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(input.toString()))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(actualResult.getResponse().getContentAsString()).isEqualTo("OK");
    }

    @Test
    void getSingleStudentTest_StudentExists() throws Exception {
        Optional<Student> expected = Optional.of(student);
        when(studentServiceMock.getSingleStudent(1001L)).thenReturn(expected);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/student/1001")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject actualResult = new JSONObject(result.getResponse().getContentAsString());

        assertThat(actualResult.get("studentId")).isEqualTo((int) student.getStudentId());
        assertThat(actualResult.get("email")).isEqualTo(student.getEmail());
    }

    @Test
    void getSingleStudentTest_NoStudentExists() {
        when(studentServiceMock.getSingleStudent(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(ServletException.class, () -> mockMvc.perform(MockMvcRequestBuilders.get("/student/1001")));
    }

    @Test
    void getAllStudentsTest_StudentExists() throws Exception {
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);

        when(studentServiceMock.getAllStudent()).thenReturn(studentList);

        MvcResult actualResult = mockMvc.perform(MockMvcRequestBuilders.get("/student"))
                .andExpect(status().isOk())
                .andReturn();

        JSONArray arrayResult = new JSONArray(actualResult.getResponse().getContentAsString());
        long resultStudentId = Long.parseLong(new JSONObject(arrayResult.get(0).toString()).get("studentId").toString());

        assertThat(arrayResult.length()).isEqualTo(studentList.size());
        assertThat(resultStudentId).isEqualTo(studentList.get(0).getStudentId());
    }

    @Test
    void getAllStudentsTest_NoStudentExists() throws Exception {
        List<Student> studentList = new ArrayList<>();
        when(studentServiceMock.getAllStudent()).thenReturn(studentList);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/student"))
                .andExpect(status().isOk())
                .andReturn();

        JSONArray actualResult = new JSONArray(result.getResponse().getContentAsString());

        assertThat(actualResult.length()).isEqualTo(studentList.size());
    }

    @Test
    void updateStudentTest() throws Exception{
        when(studentServiceMock.saveOrUpdate(any())).thenReturn("Success");

        JSONObject input = new JSONObject();
        input.put("id", 1000L);
        input.put("name", "Ram");
        input.put("email", "ram@mail.com");

        MvcResult actualResult = mockMvc.perform(MockMvcRequestBuilders.put("/update-student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input.toString()))
                .andExpect(status().isOk())
                .andReturn();

        assertThat(actualResult.getResponse().getContentAsString()).isEqualTo("OK");
    }

    @Test
    void importDataFromCSVTest_Success() throws Exception {
        when(studentServiceMock.readFromCSV(anyString())).thenReturn("OK");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/student/import"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResult = result.getResponse().getContentAsString();
        assertThat(actualResult).isEqualTo("OK");
    }

    @Test
    void exportDataToCSVTest_Success() throws Exception{
        when(studentServiceMock.writeToCSV(anyString())).thenReturn("OK");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/student/export"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResult = result.getResponse().getContentAsString();
        assertThat(actualResult).isEqualTo("OK");
    }
}
