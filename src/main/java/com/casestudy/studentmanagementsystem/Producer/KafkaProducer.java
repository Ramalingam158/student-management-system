package com.casestudy.studentmanagementsystem.Producer;

import com.casestudy.studentmanagementsystem.Dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, StudentDTO> kafkaTemplate;

    public void send(StudentDTO studentDTO) {
        kafkaTemplate.send("t-SMS", studentDTO);
    }
}