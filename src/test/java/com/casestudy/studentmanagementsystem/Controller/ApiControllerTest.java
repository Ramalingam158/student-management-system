package com.casestudy.studentmanagementsystem.Controller;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.casestudy.studentmanagementsystem.Service.StudentService;

public class ApiControllerTest {
    @InjectMocks
    ApiController apiController;

    @Mock
    StudentService studentService;
}
