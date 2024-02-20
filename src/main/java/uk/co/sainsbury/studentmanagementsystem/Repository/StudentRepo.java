package uk.co.sainsbury.studentmanagementsystem.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import uk.co.sainsbury.studentmanagementsystem.Model.Student;

@Repository
public interface StudentRepo extends CrudRepository<Student, Long> {

}
