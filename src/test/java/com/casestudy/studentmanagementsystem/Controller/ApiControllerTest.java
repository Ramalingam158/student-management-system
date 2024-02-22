package com.casestudy.studentmanagementsystem.Controller;

import com.casestudy.studentmanagementsystem.Model.Student;
import com.casestudy.studentmanagementsystem.Service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ApiControllerTest {
    @InjectMocks
    ApiController apiController;

    @Mock
    StudentService studentServiceMock;

    @Test
    void saveSingleStudentTest() throws Exception {
        JSONObject input = new JSONObject();
        input.put("id", 1000L);
        input.put("name", "Ram");
        input.put("email", "ram@mail.com");

        when(studentServiceMock.saveOrUpdate(any())).thenReturn("OK");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
        MvcResult actualResult = mockMvc.perform(MockMvcRequestBuilders.post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input.toString()))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals("OK", actualResult.getResponse().getContentAsString());
    }

    @Test
    void getSingleStudentTest_StudentExists() throws Exception {

        Student student = new Student(1001L, "Ram", "ram@mail.com");
        Optional<Student> input = Optional.of(student);

        when(studentServiceMock.getSingleStudent(1001L)).thenReturn(input);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
        MvcResult actualResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/1001"))
                .andExpect(status().isOk())
                .andReturn();

        String content = actualResult.getResponse().getContentAsString();
        Student actual = new ObjectMapper().readValue(content, Student.class);

        Assertions.assertEquals(actual.getStudentId(), 1001L);
        Assertions.assertEquals(actual.getEmail(), "ram@mail.com");
    }

    @Test
    void getSingleStudentTest_NoStudentExists() {

        when(studentServiceMock.getSingleStudent(anyLong())).thenReturn(Optional.empty());

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
        Assertions.assertThrows(ServletException.class, () -> mockMvc.perform(MockMvcRequestBuilders.get("/student/1001")));
    }

    @Test
    void getAllStudentsTest_StudentExists() throws Exception {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1001L, "Ram", "ram@mail.com"));
        when(studentServiceMock.getAllStudent()).thenReturn(studentList);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
        MvcResult actualResult = mockMvc.perform(MockMvcRequestBuilders.get("/student"))
                .andExpect(status().isOk())
                .andReturn();

        String content = actualResult.getResponse().getContentAsString();
        JSONArray result = new JSONArray(content);

        Assertions.assertEquals(studentList.size(), result.length());
        String res = new JSONObject(result.get(0).toString()).get("studentId").toString();
        Assertions.assertEquals(studentList.get(0).getStudentId(), Long.parseLong(res));
    }

    @Test
    void getAllStudentsTest_NoStudentExists() throws Exception {
        List<Student> studentList = new ArrayList<>();
        when(studentServiceMock.getAllStudent()).thenReturn(studentList);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
        MvcResult actualResult = mockMvc.perform(MockMvcRequestBuilders.get("/student"))
                .andExpect(status().isOk())
                .andReturn();

        String content = actualResult.getResponse().getContentAsString();
        JSONArray result = new JSONArray(content);

        Assertions.assertEquals(studentList.size(), result.length());
    }

    @Test
    void updateStudentTest() throws Exception{

        when(studentServiceMock.saveOrUpdate(any())).thenReturn("Success");

        JSONObject input = new JSONObject();
        input.put("id", 1000L);
        input.put("name", "Ram");
        input.put("email", "ram@mail.com");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
        MvcResult actualResult = mockMvc.perform(MockMvcRequestBuilders.put("/update-student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input.toString()))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals("OK", actualResult.getResponse().getContentAsString());
    }

    @Test
    void importDataFromCSVTest_Success() throws Exception {
        when(studentServiceMock.readFromCSV(anyString())).thenReturn("OK");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/student/import"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResult = result.getResponse().getContentAsString();
        Assertions.assertEquals("OK", actualResult);
    }

    @Test
    void importDataFromCSVTest_Failure() throws Exception {
        when(studentServiceMock.readFromCSV(anyString())).thenReturn("IO Exception");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/student/import"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResult = result.getResponse().getContentAsString();
        Assertions.assertEquals("IO Exception", actualResult);
    }

    @Test
    void exportDataToCSVTest_Success() throws Exception{
        when(studentServiceMock.writeToCSV(anyString())).thenReturn("OK");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/student/export"))
                .andExpect(status().isOk())
                .andReturn();

        String actualResult = result.getResponse().getContentAsString();
        Assertions.assertEquals("OK", actualResult);
    }

    @Test
    void exportDataToCSV_Failure() throws Exception {
        when(studentServiceMock.writeToCSV(anyString())).thenReturn("IO Exception");

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/student/export"))
                .andExpect(status().isOk())
                .andReturn();
        String actualResult = result.getResponse().getContentAsString();
        Assertions.assertEquals("IO Exception", actualResult);
    }
}
