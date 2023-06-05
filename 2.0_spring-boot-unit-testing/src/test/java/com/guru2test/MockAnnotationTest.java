package com.guru2test;

import com.guru2test.dao.ApplicationDao;
import com.guru2test.models.CollegeStudent;
import com.guru2test.models.StudentGrades;
import com.guru2test.service.ApplicationService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
public class MockAnnotationTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;

    //Create Mock for the DAO
    @Mock
    private ApplicationDao applicationDao;

    //Inject mock into Service
    @InjectMocks
    private ApplicationService applicationService;

    public void beforeEach(){
        studentOne.setFirstname("Meher");
        studentOne.setLastname("khe");
        studentOne.setEmailAddress("meherkhe@guru2test.com");
        studentOne.setStudentGrades(studentGrades);
    }


}
