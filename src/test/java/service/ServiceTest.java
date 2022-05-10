package service;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {
    public static Service service;

    @org.junit.jupiter.api.BeforeAll
    static void setUp() {
        Validator<Student> studentValidator = new StudentValidator();
        Validator<Homework> homeworkValidator = new HomeworkValidator();
        Validator<Grade> gradeValidator = new GradeValidator();

        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
        HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
        GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");
        service = new Service(fileRepository1, fileRepository2, fileRepository3);
    }

    @org.junit.jupiter.api.Test
    void saveNewStudentTest() {
        assertEquals(0,service.saveStudent("6", "Dave", 245));
    }

    @org.junit.jupiter.api.Test
    void deleteExistingStudentTest() {
        assertEquals(0,service.deleteStudent("6"));
    }

    @org.junit.jupiter.api.Test
    void findAllStudents() {
        Iterable<Student> studentList = service.findAllStudents();
        assertNotNull(studentList);
    }

    @ParameterizedTest
    @ValueSource(strings = {"3", "-3", "15", "x"})
    void deleteHomeworkParametrized(String input) {
        assertEquals(0, service.deleteHomework(input));
    }

    @ParameterizedTest
    @NullSource
    void saveNullHomework(String input) {
        Integer n = null;
        assertThrows(NullPointerException.class, () -> { service.saveHomework(input, input, n, n); });
    }
}