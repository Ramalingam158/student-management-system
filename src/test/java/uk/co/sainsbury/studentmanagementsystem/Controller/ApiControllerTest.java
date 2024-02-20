package uk.co.sainsbury.studentmanagementsystem.Controller;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import uk.co.sainsbury.studentmanagementsystem.Service.StudentService;

public class ApiControllerTest {
    @InjectMocks
    ApiController apiController;

    @Mock
    StudentService studentService;
}
