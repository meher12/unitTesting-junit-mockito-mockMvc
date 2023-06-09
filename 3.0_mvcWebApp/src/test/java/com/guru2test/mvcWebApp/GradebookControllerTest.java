package com.guru2test.mvcWebApp;

import com.guru2test.mvcWebApp.models.CollegeStudent;
import com.guru2test.mvcWebApp.models.GradebookCollegeStudent;
import com.guru2test.mvcWebApp.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("/application.properties")
// Add annotation
@AutoConfigureMockMvc
class GradebookControllerTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// Inject the MockMvc
	@Autowired
	private MockMvc mockMvc;

	@Mock
	private StudentAndGradeService studentAndGradeServiceMock;

	@BeforeEach
	public void setupDataBase(){
		jdbcTemplate.execute("insert into student(id, firstname, lastname, email_address)" +
				"values (1, 'Eric', 'Roby', 'eric.roby@guru2test_school.com')");
	}

	@Test
	public void getStudentHttpRequest() throws Exception{

		CollegeStudent studentOne = new GradebookCollegeStudent("Eric", "Roby",
				"eric.roby@guru2test_school.com");
		CollegeStudent studentTwo = new GradebookCollegeStudent("Chad", "Darby",
				"chad.darby@guru2test_school.com");

        // Create expected result
		List<CollegeStudent> collegeStudentList = new ArrayList<>(Arrays.asList(studentOne, studentTwo));

		//When method getGradebook(..) is called then return collegeStudentList
		when(studentAndGradeServiceMock.getGradebook()).thenReturn(collegeStudentList);

		//Call method under test and assert results
		assertIterableEquals(collegeStudentList, studentAndGradeServiceMock.getGradebook());

		// Perform web request

		// Define expectations


	}
	@AfterEach
	public void setupAfterTransaction(){
		jdbcTemplate.execute("DELETE FROM student");
	}


}