package com.guru2test;

import com.guru2test.models.CollegeStudent;
import com.guru2test.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SpringBootUnitTestingApplication.class)
public class ReflectionTestUtilsTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;

    @BeforeEach
    public void studentBeforeEach() {
        studentOne.setFirstname("Eric");
        studentOne.setLastname("Toby");
        studentOne.setEmailAddress("eric.toby@guru2test.com");
        studentOne.setStudentGrades(studentGrades);

        // Set private fields directly
        ReflectionTestUtils.setField(studentOne,"id",1);
        ReflectionTestUtils.setField(studentOne, "studentGrades",
                new StudentGrades(new ArrayList<>(Arrays.asList(
                        100.0, 85.0, 76.50, 91.75))));
    }

    // get private field
    @Test
    public void getPrivateField(){
        assertEquals(1, ReflectionTestUtils.getField(studentOne,"id"));
    }

    // invoke private method
    @Test
    public void invokePrivateMethod(){
        assertEquals("Eric 1",
        ReflectionTestUtils.invokeMethod(studentOne,"getFirstNameAndId"),
                "Fail private method not call");
    }
}
