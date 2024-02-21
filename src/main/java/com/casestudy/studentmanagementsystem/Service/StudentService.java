package com.casestudy.studentmanagementsystem.Service;

import com.casestudy.studentmanagementsystem.Model.Student;
import com.casestudy.studentmanagementsystem.Repository.StudentRepo;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class StudentService {
    final StudentRepo studentRepo;

    public String saveOrUpdate(Student student) {
        studentRepo.save(student);
        return "Success";
    }

    public Student getSingleStudent(long id) {
         return studentRepo.findById(id).get();
    }

    public List<Student> getAllStudent() {
        return new ArrayList<>(studentRepo.findAll());
    }

    public String readFromCSV(String filePath) {
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
            return "IO Exception";
        }

        return "Success";
    }

    public String writeToCSV(String filePath) {

        List<Student> studentList = new ArrayList<>();
        studentRepo.findAll().forEach(studentList::add);

        try(CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath + "\\export.csv"))) {
            csvWriter.writeNext(new String[] {"Id", "Name", "E-Mail ID"});
            studentList.forEach((student) -> csvWriter.writeNext(new String[] {String.valueOf(student.getStudentId()), student.getName(), student.getEmail()}));

            Stream<Path> files = Files.list(Paths.get(filePath));
            long count = files.count();
            files.close();

            if(count == 1) {
                return "Success";
            } else {
                return "File Not Created";
            }

        } catch (IOException e) {
            return "IO Exception";
        }
    }
}
