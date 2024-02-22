package com.casestudy.studentmanagementsystem.Exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class ExceptionControllerTest {
    @InjectMocks
    ExceptionController exceptionController;

    @Test
    void noSuchStudentExceptionHandlerTest() {
        ResponseEntity<Map<String, String>> response = exceptionController.noSuchStudentExceptionHandler();
        Assertions.assertEquals("No such student Exist with this ID", response.getBody().get("Error"));
    }
}
