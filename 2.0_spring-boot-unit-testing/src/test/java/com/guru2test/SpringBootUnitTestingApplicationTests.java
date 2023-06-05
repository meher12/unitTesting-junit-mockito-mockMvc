package com.guru2test;

import com.guru2test.models.CollegeStudent;
import com.guru2test.models.StudentGrades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class SpringBootUnitTestingApplicationTests {

	/*@Test
	void contextLoads() {
	}*/

	private static int count  = 0;

	@Value("${info.app.name}")
	private String appInfo;

	@Value("${info.app.description}")
	private String appDescription;

	@Value("${info.school.name}")
	private String schoolName;

	@Value("${info.app.version}")
	private String appVersion;

	@Autowired
	CollegeStudent student;

	@Autowired
	StudentGrades studentGrades;

	@BeforeEach
	public void beforeEach(){
		count = count + 1 ;
		System.out.println("Testing: " + appInfo + " which is" + appDescription +
				" Version: " + appVersion+ " Excution of test method "+ count);
		student.setFirstname("Eric");
		student.setLastname("Roby");
		student.setEmailAddress("eric.roby@guru2test.com");
		studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.0, 91.75)));
		student.setStudentGrades(studentGrades);
	}
	@DisplayName("Add grade results for student grades equals")
	@Test
	public void addGradeResultsForStudentGradesAssertEquals(){
		assertEquals(352.75, studentGrades.addGradeResultsForSingleClass(
				student.getStudentGrades().getMathGradeResults()
		));
	}

	@DisplayName("Add grade results for student grades not equals")
	@Test
	public void addGradeResultsForStudentGradesAssertNotEquals(){
		assertNotEquals(0, studentGrades.addGradeResultsForSingleClass(
				student.getStudentGrades().getMathGradeResults()
		));
	}








	@Test
	void basicTest(){

	}

}
