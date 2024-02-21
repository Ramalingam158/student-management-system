package com.casestudy.studentmanagementsystem.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ProductExceptionController {
    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> noSuchStudentException() {
        Map<String, String> JSONResponse = new HashMap<>();
        JSONResponse.put("Error", "No such student Exist with this ID");
        return new ResponseEntity<>(JSONResponse, HttpStatus.NOT_FOUND);
    }
}
