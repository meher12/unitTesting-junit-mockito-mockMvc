package com.guru2test.mvcWebApp;

import com.guru2test.mvcWebApp.models.CollegeStudent;
import com.guru2test.mvcWebApp.repository.StudentDao;
import com.guru2test.mvcWebApp.service.StudentAndGradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource("/application.properties")
public class StudentAndGradeServiceTest {

    @Autowired
    private StudentAndGradeService  studentService;

    @Autowired
    private StudentDao studentDao;
    @Test
    public void createStudentService(){

        studentService.createStudent("Chad", "Darby", "chad.darby@guru2test_school.com");

        CollegeStudent student = studentDao.findByEmailAddress("chad.darby@guru2test_school.com");

        assertEquals("chad.darby@guru2test_school.com", student.getEmailAddress(), "find by email");

   }
}
