package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ServiceMockTest {
    @InjectMocks
    public static Service mockService;
    @Mock
    static StudentXMLRepository studentXMLRepository;
    @Mock
    static HomeworkXMLRepository homeworkXMLRepository;
    @Mock
    static GradeXMLRepository gradeXMLRepository;

    @Mock
    static Validator<Student> studentValidator;
    @Mock
    static Validator<Homework> homeworkValidator;
    @Mock
    static Validator<Grade> gradeValidator;

    @org.junit.jupiter.api.BeforeAll
    static void setUp() {
        studentValidator = mock(StudentValidator.class);
        homeworkValidator = mock(HomeworkValidator.class);
        gradeValidator = mock(GradeValidator.class);

        studentXMLRepository = mock(StudentXMLRepository.class);
        homeworkXMLRepository = mock(HomeworkXMLRepository.class);
        gradeXMLRepository = mock(GradeXMLRepository.class);
        mockService = new Service(studentXMLRepository, homeworkXMLRepository, gradeXMLRepository);
    }

    @org.junit.jupiter.api.BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @org.junit.jupiter.api.Test
    void saveNewStudentTest() {
        Student student = new Student("6", "Dave", 245);
        when(studentXMLRepository.save(any(Student.class))).thenReturn(student);
        int returnValue = mockService.saveStudent("6", "Dave", 245);
        Mockito.verify(studentXMLRepository).save(any(Student.class));
        assertEquals(1, returnValue);
    }

    @org.junit.jupiter.api.Test
    void deleteExistingStudentTest() {
        Student student = new Student("6", "Dave", 245);
        when(studentXMLRepository.delete(anyString())).thenReturn(student);
        int returnValue = mockService.deleteStudent("6");
        Mockito.verify(studentXMLRepository).delete(anyString());
        assertEquals(0, returnValue);
    }

    @org.junit.jupiter.api.Test
    void saveNullHomework() {
        Integer nullint = null;
        String nullstr = null;
        when(homeworkXMLRepository.save(any(Homework.class))).thenReturn(null);
        Mockito.verify(homeworkXMLRepository, times(0)).save(any(Homework.class));
        assertThrows(NullPointerException.class, () -> { mockService.saveHomework(nullstr, nullstr, nullint, nullint); });
    }
}
