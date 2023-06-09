package com.guru2test.mvcWebApp;

import com.guru2test.mvcWebApp.models.CollegeStudent;
import com.guru2test.mvcWebApp.models.GradebookCollegeStudent;
import com.guru2test.mvcWebApp.repository.StudentDao;
import com.guru2test.mvcWebApp.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource("/application.properties")
// Add annotation
@AutoConfigureMockMvc
class GradebookControllerTest {


	private static MockHttpServletRequest request;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// Inject the MockMvc
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private StudentDao studentDao;

	@Mock
	private StudentAndGradeService studentAndGradeServiceMock;

	@BeforeAll
	public static  void setup(){
		// create th object to insert in the db by MockHttpServletRequest
		request = new MockHttpServletRequest();
		request.setParameter("firstname", "Maher");
		request.setParameter("lastname", "khe");
		request.setParameter("emailAddress", "maher.khe@guru2test_school.com");
	}

	@BeforeEach
	public void setupDataBase(){
		jdbcTemplate.execute("insert into student(id, firstname, lastname, email_address)" +
				"values (10, 'Eric', 'Roby', 'eric.roby@guru2test_school.com')");
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

		// Perform a GET request to "/" Setting expectation for status OK
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/"))
				.andExpect(status().isOk()).andReturn();

		ModelAndView mav = mvcResult.getModelAndView();

		// index is the view name (page name in html)
		ModelAndViewAssert.assertViewName(mav, "index");

	}

	// Create a student in the database
	@Test
	public void createStudentHttpRequest() throws Exception{

		CollegeStudent studentOne = new GradebookCollegeStudent("Eric", "Roby",
				"eric.roby@guru2test_school.com");
		List<CollegeStudent> collegeStudentList = new ArrayList<>(Arrays.asList(studentOne));

		when(studentAndGradeServiceMock.getGradebook()).thenReturn(collegeStudentList);

		assertIterableEquals(collegeStudentList, studentAndGradeServiceMock.getGradebook());

		MvcResult mvcResult = this.mockMvc.perform(post("/")
						.contentType(MediaType.APPLICATION_JSON)
						.param("firstname", request.getParameterValues("firstname"))
						.param("lastname", request.getParameterValues("lastname"))
						.param("emailAddress", request.getParameterValues("emailAddress")))
				         .andExpect(status().isOk()).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		ModelAndViewAssert.assertViewName(mav,  "index");

		// verify result if exist in the database
		 CollegeStudent verifyStudent = studentDao.findByEmailAddress("maher.khe@guru2test_school.com");
		 assertNotNull(verifyStudent, "Student should be found");
	}

	@Test
	public void deleteStudentHttpRequest() throws Exception {

		assertTrue(studentDao.findById(10).isPresent());

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
						.get("/delete/student/{id}", 10))
				.andExpect(status().isOk()).andReturn();

		ModelAndView mav = mvcResult.getModelAndView();

		ModelAndViewAssert.assertViewName(mav, "index");

		// MAke sure student was deleted
		assertFalse(studentDao.findById(10).isPresent());
	}

	@Test
	public void deleteStudentHttpRequestErrorPage() throws Exception {

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
						.get("/delete/student/{id}", 0))
				.andExpect(status().isOk()).andReturn();

		ModelAndView mav = mvcResult.getModelAndView();

		ModelAndViewAssert.assertViewName(mav, "error");
	}
	@AfterEach
	public void setupAfterTransaction(){
		jdbcTemplate.execute("DELETE FROM student");
	}


}
