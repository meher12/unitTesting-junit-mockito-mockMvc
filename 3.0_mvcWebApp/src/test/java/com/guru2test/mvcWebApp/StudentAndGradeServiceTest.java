package com.guru2test.mvcWebApp;

import com.guru2test.mvcWebApp.models.CollegeStudent;
import com.guru2test.mvcWebApp.repository.StudentDao;
import com.guru2test.mvcWebApp.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("/application.properties")
public class StudentAndGradeServiceTest {

    @Autowired
    private StudentAndGradeService  studentService;

    @Autowired
    private StudentDao studentDao;

    // JdbcTemplate is a class in the Spring JDBC framework which simplifies the use of JDBC and helps to avoid common errors. It provides many methods for querying and updating databases. Spring Boot automatically
    // creates a JdbcTemplate when it detects H2
    @Autowired
    private JdbcTemplate jdbcTemplate;
    // Insert a sample data
    @BeforeEach
    public void setupDataBase(){
        jdbcTemplate.execute("insert into student(id, firstname, lastname, email_address)" +
                "values (1, 'Eric', 'Roby', 'eric.roby@guru2test_school.com')");

    }
    @Test
    public void createStudentService(){

        studentService.createStudent("Chad", "Darby", "chad.darby@guru2test_school.com");

        CollegeStudent student = studentDao.findByEmailAddress("chad.darby@guru2test_school.com");

        assertEquals("chad.darby@guru2test_school.com", student.getEmailAddress(), "find by email");
   }

   @Test
   @DisplayName("Find student by id")
   public void isStudentNullCheck(){

        // return true because exist in the DB
        assertTrue(studentService.checkIfStudentIsNull(1));
       // return false because not exist in the DB
        assertFalse(studentService.checkIfStudentIsNull(0));
   }

   @AfterEach
   public void setupAfterTransaction(){
        jdbcTemplate.execute("DELETE FROM student");
   }

}
