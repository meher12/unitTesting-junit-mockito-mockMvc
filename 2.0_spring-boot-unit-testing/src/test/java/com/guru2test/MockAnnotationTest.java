package com.guru2test;

import com.guru2test.dao.ApplicationDao;
import com.guru2test.models.CollegeStudent;
import com.guru2test.models.StudentGrades;
import com.guru2test.service.ApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MockAnnotationTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    CollegeStudent studentOne;

    @Autowired
    StudentGrades studentGrades;

    //Create Mock for the DAO
    //@Mock
    @MockBean
    private ApplicationDao applicationDao;

    //Inject mock dependencies into Service
   // @InjectMocks
    @Autowired
    private ApplicationService applicationService;

    @BeforeEach
    public void beforeEach() {
        studentOne.setFirstname("Maher");
        studentOne.setLastname("khe");
        studentOne.setEmailAddress("meher.khe@guru2test.com");
        studentOne.setStudentGrades(studentGrades);
    }

    @DisplayName("When & Verify")
    @Test
    public void assertEqualsTestAddGrades() {
        //When method addGradeResultsForSingleClass(..) is called then return 100.0
        when(applicationDao.addGradeResultsForSingleClass(
                studentGrades.getMathGradeResults())).thenReturn(100.00);
        //Call method under test and assert results
        assertEquals(100, applicationService.addGradeResultsForSingleClass(
                studentOne.getStudentGrades().getMathGradeResults()));

        //Verify method calls
        verify(applicationDao).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());

        verify(applicationDao, times(1)).addGradeResultsForSingleClass(
                studentGrades.getMathGradeResults());
    }
    //By @MockBean and @Annotation
    @DisplayName("Find Gpa")
    @Test
    public void assertEqualsTestFindGpa() {
        //When method findGradePointAverage(..) is called then return 88.31
        when(applicationDao.findGradePointAverage(studentGrades.getMathGradeResults()))
                .thenReturn(88.31);
        //Call method under test and assert results
        assertEquals(88.31, applicationService.findGradePointAverage(studentOne
                .getStudentGrades().getMathGradeResults()));
    }

    @DisplayName("Not Null")
    @Test
    public void testAssertNotNull() {
        when(applicationDao.checkNull(studentGrades.getMathGradeResults()))
                .thenReturn(true);
        assertNotNull(applicationService.checkNull(studentOne.getStudentGrades()
                .getMathGradeResults()), "Object should not be null");
    }

    //Throwing Exceptions with Mocks
    @DisplayName("Throw runtime error")
    @Test
    public void throwRuntimeError() {
        CollegeStudent nullStudent = (CollegeStudent) context.getBean("collegeStudent");

        //Do throw an exception when the method is called
        doThrow(new RuntimeException()).when(applicationDao).checkNull(nullStudent);

        //Assert that the exception was thrown
        assertThrows(RuntimeException.class, () -> {
            applicationService.checkNull(nullStudent);
        });

        verify(applicationDao, times(1)).checkNull(nullStudent);
    }

    @DisplayName("Multiple Stubbing")
    @Test
    public void stubbingConsecutiveCalls() {
        CollegeStudent nullStudent = (CollegeStudent) context.getBean("collegeStudent");

        when(applicationDao.checkNull(nullStudent))
                .thenThrow(new RuntimeException())
                .thenReturn("Do not throw exception second time");

        assertThrows(RuntimeException.class, () -> {
            applicationService.checkNull(nullStudent);
        });

        assertEquals("Do not throw exception second time",
                applicationService.checkNull(nullStudent));

        verify(applicationDao, times(2)).checkNull(nullStudent);
    }







}
