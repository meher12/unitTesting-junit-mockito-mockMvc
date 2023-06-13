package com.guru2test.mvcWebApp;

import com.guru2test.mvcWebApp.models.*;
import com.guru2test.mvcWebApp.repository.HistoryGradesDao;
import com.guru2test.mvcWebApp.repository.MathGradesDao;
import com.guru2test.mvcWebApp.repository.ScienceGradesDao;
import com.guru2test.mvcWebApp.repository.StudentDao;
import com.guru2test.mvcWebApp.service.StudentAndGradeService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${sql.script.create.student}")
    private String sqlAddStudent;

    @Value("${sql.script.create.math.grade}")
    private String sqlAddMathGrade;

    @Value("${sql.script.create.science.grade}")
    private String sqlAddScienceGrade;

    @Value("${sql.script.create.history.grade}")
    private String sqlAddHistoryGrade;

    @Value("${sql.script.delete.student}")
    private String sqlDeleteStudent;

    @Value("${sql.script.delete.math.grade}")
    private String sqlDeleteMathGrade;

    @Value("${sql.script.delete.science.grade}")
    private String sqlDeleteScienceGrade;

    @Value("${sql.script.delete.history.grade}")
    private String sqlDeleteHistoryGrade;

    // JdbcTemplate is a class in the Spring JDBC framework which simplifies the use of JDBC
    // and helps to avoid common errors.
    // It provides many methods for querying and updating databases. Spring Boot automatically
    // creates a JdbcTemplate when it detects H2
    @Autowired
    private JdbcTemplate jdbcTemplate;


    // Insert a sample data
    @BeforeEach
    public void setupDataBase() {

       jdbcTemplate.execute(sqlAddStudent);
       jdbcTemplate.execute(sqlAddMathGrade);
       jdbcTemplate.execute(sqlAddScienceGrade);
       jdbcTemplate.execute(sqlAddHistoryGrade);
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
        assertTrue(studentService.checkIfStudentIsNull(30));
        // return false because not exist in the DB
        assertFalse(studentService.checkIfStudentIsNull(0));
    }

    @Test
    public void deleteStudentService() {
        Optional<CollegeStudent> deletedCollegeStudent = studentDao.findById(30);

        Optional<MathGrade> deletedMathGrade = mathGradesDao.findById(20);
        Optional<HistoryGrade> deletedHistoryGrade = historyGradesDao.findById(20);
        Optional<ScienceGrade> deletedScienceGrade = scienceGradesDao.findById(20);


        assertTrue(deletedCollegeStudent.isPresent(), "Return True");
        assertTrue(deletedMathGrade.isPresent());
        assertTrue(deletedHistoryGrade.isPresent());
        assertTrue(deletedScienceGrade.isPresent());

        studentService.deleteStudent(30);

        deletedCollegeStudent = studentDao.findById(30);
        deletedMathGrade = mathGradesDao.findById(20);
        deletedHistoryGrade = historyGradesDao.findById(20);
        deletedScienceGrade = scienceGradesDao.findById(20);

        assertFalse(deletedCollegeStudent.isPresent(), "Return False");
        assertFalse(deletedMathGrade.isPresent());
        assertFalse(deletedHistoryGrade.isPresent());
        assertFalse(deletedScienceGrade.isPresent());

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
        assertTrue(studentService.createGrade(80.50, 30, "math"));
        assertTrue(studentService.createGrade(80.50, 30, "science"));
        assertTrue(studentService.createGrade(80.50, 30, "history"));

        // Get all grades with studentId
        Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(30);
        Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradeByStudentId(30);
        Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradeByStudentId(30);

        // Verify there id grades
        assertEquals(2, ((Collection<MathGrade>) mathGrades).size(), "Student has math grades");
        assertEquals(2, ((Collection<ScienceGrade>) scienceGrades).size(), "Student has science grades");
        assertEquals(2, ((Collection<HistoryGrade>) historyGrades).size(), "Student has history grades");
    }

    @Test
    public void createGradeServiceReturnFalse() {
        // grade outside range
        assertFalse(studentService.createGrade(105, 30, "math"));

        // grade has a negative value
        assertFalse(studentService.createGrade(-5, 30, "math"));

        // invalid studentId
        assertFalse(studentService.createGrade(80.50, 2, "math"));

        // invalid subject
        assertFalse(studentService.createGrade(80.50, 30, "literature"));
    }

    @Test
    public void deleteGradeService() {
        //If grade delete is successful, should return student id associated with the grade
        assertEquals(30, studentService.deleteGrade(20, "math"));
        assertEquals(30, studentService.deleteGrade(20, "science"));
        assertEquals(30, studentService.deleteGrade(20, "history"));
    }

    @Test
    public void deleteGradeServiceReturnStudentIdOfZero() {

        // invalid grade id
        assertEquals(0, studentService.deleteGrade(0, "science"),
                "No student should have 0 id");

        // invalid subject
        assertEquals(0, studentService.deleteGrade(0, "literature"),
                "No student should have literature class");
    }

    @Test
    public void studentInformation() {
        GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(30);
        assertNotNull(gradebookCollegeStudent);
        assertEquals(30, gradebookCollegeStudent.getId());
        assertEquals("Eric", gradebookCollegeStudent.getFirstname());
        assertEquals("Roby", gradebookCollegeStudent.getLastname());
        assertEquals("eric.roby@guru2test_school.com", gradebookCollegeStudent.getEmailAddress());
        assertEquals(30, gradebookCollegeStudent.getStudentGrades().getMathGradeResults().size());
        assertEquals(30, gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults().size());
        assertEquals(30, gradebookCollegeStudent.getStudentGrades().getScienceGradeResults().size());
    }

    @Test
    public void studentInformationServiceReturnNull() {

        GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(0);

        assertNull(gradebookCollegeStudent);
    }

    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute(sqlDeleteStudent);
        jdbcTemplate.execute(sqlAddMathGrade);
        jdbcTemplate.execute(sqlAddScienceGrade);
        jdbcTemplate.execute(sqlAddHistoryGrade);
    }

}
