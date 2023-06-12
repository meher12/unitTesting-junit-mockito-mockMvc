package com.guru2test.mvcWebApp;

import com.guru2test.mvcWebApp.models.*;
import com.guru2test.mvcWebApp.repository.HistoryGradesDao;
import com.guru2test.mvcWebApp.repository.MathGradesDao;
import com.guru2test.mvcWebApp.repository.ScienceGradesDao;
import com.guru2test.mvcWebApp.repository.StudentDao;
import com.guru2test.mvcWebApp.service.StudentAndGradeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application.properties")
public class StudentAndGradeServiceTest {

    @Autowired
    private StudentAndGradeService studentService;

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private MathGradesDao mathGradesDao;

    @Autowired
    private ScienceGradesDao scienceGradesDao;

    @Autowired
    private HistoryGradesDao historyGradesDao;

    // JdbcTemplate is a class in the Spring JDBC framework which simplifies the use of JDBC
    // and helps to avoid common errors.
    // It provides many methods for querying and updating databases. Spring Boot automatically
    // creates a JdbcTemplate when it detects H2
    @Autowired
    private JdbcTemplate jdbcTemplate;





    // Insert a sample data
    @BeforeEach
    public void setupDataBase() {


        jdbcTemplate.execute("insert into student(id, firstname, lastname, email_address) values (1, 'Eric', 'Roby', 'eric.roby@guru2test_school.com')");

        jdbcTemplate.execute("insert into math_grade(id, student_id, grade) values (2, 1, 100.00)");
        jdbcTemplate.execute("insert into science_grade(id, student_id, grade) values (2, 1, 100.00)");
        jdbcTemplate.execute("insert into history_grade(id, student_id, grade) values (2, 1, 100.00)");
    }

    @Test
    public void createStudentService() {

        studentService.createStudent("Chad", "Darby", "chad.darby@guru2test_school.com");

        CollegeStudent student = studentDao.findByEmailAddress("chad.darby@guru2test_school.com");

        assertEquals("chad.darby@guru2test_school.com", student.getEmailAddress(), "find by email");
    }

    @Test
    @DisplayName("Find student by id")
    public void isStudentNullCheck() {

        // return true because exist in the DB
        assertTrue(studentService.checkIfStudentIsNull(1));
        // return false because not exist in the DB
        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    public void deleteStudentService() {
        Optional<CollegeStudent> deletedCollegeStudent = studentDao.findById(1);

        assertTrue(deletedCollegeStudent.isPresent(), "return True");
        studentService.deleteStudent(1);

        deletedCollegeStudent = studentDao.findById(1);
        assertFalse(deletedCollegeStudent.isPresent(), "Return False");

    }

    @Sql("/insertData.sql")
    @Test
    public void getGradebookService() {
        Iterable<CollegeStudent> iterableCollegeStudents = studentService.getGradebook();
        List<CollegeStudent> collegeStudents = new ArrayList<>();
        for (CollegeStudent collegeStudent : iterableCollegeStudents) {
            collegeStudents.add(collegeStudent);
        }
        // Test passed because we have one student inserted by  @BeforeEach and 4 student in the sql Data file
        assertEquals(5, collegeStudents.size());
    }

    @Test
    public void createGradeService() {

        // Create the grade : grade=80.50, id=1, type="math
        assertTrue(studentService.createGrade(80.50, 1, "math"));
        assertTrue(studentService.createGrade(80.50, 1, "science"));
        assertTrue(studentService.createGrade(80.50, 1, "history"));

        // Get all grades with studentId
        Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(1);
        Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradeByStudentId(1);
        Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradeByStudentId(1);

        // Verify there id grades
        assertTrue(((Collection<MathGrade>) mathGrades).size() == 2, "Student has math grades");
        assertTrue(((Collection<ScienceGrade>)scienceGrades).size() == 2, "Student has science grades");
        assertTrue(((Collection<HistoryGrade>) historyGrades).size() == 2, "Student has history grades");
    }

    @Test
    public void createGradeServiceReturnFalse() {
        // grade outside range
        assertFalse(studentService.createGrade(105, 1, "math"));

        // grade has a negative value
        assertFalse(studentService.createGrade(-5, 1, "math"));

        // invalid studentId
        assertFalse(studentService.createGrade(80.50, 2, "math"));

        // invalid subject
        assertFalse(studentService.createGrade(80.50, 1, "literature"));
    }

    @Test
    public void deleteGradeService(){
        //If grade delete is successful, should return student id associated with the grade
        assertEquals(1, studentService.deleteGrade(2, "math"));
        assertEquals(1, studentService.deleteGrade(2, "science"));
        assertEquals(1, studentService.deleteGrade(2, "history"));
    }

    @Test
    public void deleteGradeServiceReturnStudentIdOfZero(){

        // invalid grade id
        assertEquals(0, studentService.deleteGrade(0, "science"),
                "No student should have 0 id");

        // invalid subject
        assertEquals(0, studentService.deleteGrade(0, "literature"),
                "No student should have literature class");
    }
    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute("DELETE FROM student");
        jdbcTemplate.execute("DELETE FROM math_grade");
        jdbcTemplate.execute("DELETE FROM science_grade");
        jdbcTemplate.execute("DELETE FROM history_grade");
    }

}
