package com.casestudy.studentmanagementsystem.Model;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {
    @CsvBindByName(column = "studentId")
    @Id
    private long studentId;

    @CsvBindByName(column = "name")
    @Column
    private String name;

    @CsvBindByName(column = "email")
    @Column
    private String email;

}
