package com.casestudy.studentmanagementsystem.Consumer;

import com.casestudy.studentmanagementsystem.Dto.StudentDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaConsumer {

    private List<StudentDTO> studentList = new ArrayList<>();

    @KafkaListener(topics = "t-SMS")
    public void consume(StudentDTO studentDTO) {
        studentList.add(studentDTO);
        System.out.println("Consumed: " + studentDTO);
    }

    public List<StudentDTO> getConsumedMsgs() {
        return studentList;
    }
}
