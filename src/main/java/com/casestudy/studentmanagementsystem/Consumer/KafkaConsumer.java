package com.casestudy.studentmanagementsystem.Consumer;

import com.casestudy.studentmanagementsystem.Dto.StudentDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "t-SMS")
    public void consume(StudentDTO studentDTO) {
        System.out.println("Consumed: " + studentDTO);
    }
}
