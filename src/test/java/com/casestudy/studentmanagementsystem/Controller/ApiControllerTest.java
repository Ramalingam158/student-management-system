package com.casestudy.studentmanagementsystem.Controller;

import com.casestudy.studentmanagementsystem.Model.Student;
import com.casestudy.studentmanagementsystem.Service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ApiControllerTest {
    @InjectMocks
    ApiController apiController;

    @Mock
    StudentService studentServiceMock;

    @Autowired
    private MockMvc mvc;

    @Test
    void saveSingleStudentTest() throws Exception {
        JSONObject input = new JSONObject();
            input.put("id", 1000L);
            input.put("name", "Ram");
            input.put("email", "ram@mail.com");

            when(studentServiceMock.saveOrUpdate(any())).thenReturn("Success");

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

        when(studentServiceMock.getSingleStudent(1001L)).thenReturn(new Student(1001L, "Ram", "ram@mail.com"));

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
    void getSingleStudentTest_NoStudentExists() throws Exception {

        when(studentServiceMock.getSingleStudent(anyLong())).thenThrow(ArrayIndexOutOfBoundsException.class);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
        MvcResult actualResult = mockMvc.perform(MockMvcRequestBuilders.get("/student/1001")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        String content = actualResult.getResponse().getContentAsString();
        System.out.println(content);
        JSONObject actual = new JSONObject(content);

        Assertions.assertEquals("No such student Exist with this ID", actual.get("error"));
    }

    @Test
    void getAllStudentsTest() throws Exception {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1001L, "Ram", "ram@mail.com"))
        when(studentServiceMock.getAllStudent()).thenReturn(studentList);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
        MvcResult actualResult = mockMvc.perform(MockMvcRequestBuilders.get("/student"))
                .andExpect(status().isOk())
                .andReturn();

        String content = actualResult.getResponse().getContentAsString();

    }
}
