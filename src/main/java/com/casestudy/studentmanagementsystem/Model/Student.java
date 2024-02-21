package com.casestudy.studentmanagementsystem.Model;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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

    @Override
    public String toString() {
        return "{" +
                "id=" + studentId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
