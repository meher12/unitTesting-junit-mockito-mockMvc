package com.guru2test.mvcWebApp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("/application.properties")
public class StudentAndGradeServiceTest {

    @Test
    public void createStudentService(){
        //this code generate a fail test because studentService not declared
        studentService.createStudent("Chad", "Darby", "chad.darby@guru2test_school.com");

   }
}
