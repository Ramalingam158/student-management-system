package uk.co.sainsbury.studentmanagementsystem.Service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.co.sainsbury.studentmanagementsystem.Model.Student;
import uk.co.sainsbury.studentmanagementsystem.Repository.StudentRepo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    final StudentRepo studentRepo;
    final int serialId = 100;
    public void saveOrUpdate(Student student) {
        studentRepo.save(student);
    }

    public Student getSingleStudent(long id) {
        return studentRepo.findById(id).get();
    }

    public List<Student> getAllStudent() {
        List<Student> studentList = new ArrayList<>();
        studentRepo.findAll().forEach(studentList::add);
        return studentList;
    }

    public void readFromCSV() {
        String filePath = "D:\\Projects\\student-management-system\\data.csv";

        try (Reader reader = new FileReader(filePath)) {

            // Create CSV reader
            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(1)
                    .build();

            // Create CSV to Bean object
            ColumnPositionMappingStrategy<Student> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Student.class);
            String[] columns = {"studentId", "name", "email"};
            strategy.setColumnMapping(columns);

            // Parse CSV into beans
            CsvToBean<Student> csvToBean = new CsvToBeanBuilder<Student>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();

            List<Student> studentList = csvToBean.parse();

            studentRepo.saveAll(studentList);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToCSV() {
        String filePath = "D:\\Projects\\student-management-system\\export.csv";

        List<Student> studentList = new ArrayList<>();
        studentRepo.findAll().forEach(studentList::add);

        try(CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath));) {
            csvWriter.writeNext(new String[] {"Id", "Name", "E-Mail ID"});
            studentList.forEach((student) -> csvWriter.writeNext(new String[] {String.valueOf(student.getStudentId()), student.getName(), student.getEmail()}));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
