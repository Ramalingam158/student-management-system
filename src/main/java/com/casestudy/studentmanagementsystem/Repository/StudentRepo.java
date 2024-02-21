package com.casestudy.studentmanagementsystem.Repository;

import com.casestudy.studentmanagementsystem.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {

}
