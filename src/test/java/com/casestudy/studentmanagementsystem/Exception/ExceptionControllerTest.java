package com.casestudy.studentmanagementsystem.Exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ExceptionControllerTest {
    @InjectMocks
    ExceptionController exceptionController;

    @Test
    void noSuchStudentExceptionHandlerTest() {
        ResponseEntity<Map<String, String>> response = exceptionController.noSuchStudentExceptionHandler();
        assertThat(response.getBody().get("Error")).isEqualTo("No such student Exist with this ID");
    }
}
