package com.guru2test.mvcWebApp;

import com.guru2test.mvcWebApp.models.CollegeStudent;
import com.guru2test.mvcWebApp.models.GradebookCollegeStudent;
import com.guru2test.mvcWebApp.models.MathGrade;
import com.guru2test.mvcWebApp.repository.MathGradesDao;
import com.guru2test.mvcWebApp.repository.StudentDao;
import com.guru2test.mvcWebApp.service.StudentAndGradeService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Optional;

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

    @Autowired
    private StudentAndGradeService studentAndGradeService;

    @Mock
    private StudentAndGradeService studentAndGradeServiceMock;

    // Inject MathGradesDao
    @Autowired
    private MathGradesDao mathGradesDao;

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

    @BeforeAll
    public static void setup() {
        // create th object to insert in the db by MockHttpServletRequest
        request = new MockHttpServletRequest();
        request.setParameter("firstname", "Maher");
        request.setParameter("lastname", "khe");
        request.setParameter("emailAddress", "maher.khe@guru2test_school.com");
    }

    @BeforeEach
    public void setupDataBase() {
	/*	jdbcTemplate.execute("insert into student(id, firstname, lastname, email_address)" +
				"values (10, 'Eric', 'Roby', 'eric.roby@guru2test_school.com')");*/
        jdbcTemplate.execute(sqlAddStudent);
        jdbcTemplate.execute(sqlAddMathGrade);
        jdbcTemplate.execute(sqlAddScienceGrade);
        jdbcTemplate.execute(sqlAddHistoryGrade);
    }

    @Test
    public void getStudentHttpRequest() throws Exception {

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
    public void createStudentHttpRequest() throws Exception {

        CollegeStudent studentOne = new GradebookCollegeStudent("Eric", "Roby",
                "eric.roby@guru2test_school.com");
        List<CollegeStudent> collegeStudentList = new ArrayList<>(List.of(studentOne));

        when(studentAndGradeServiceMock.getGradebook()).thenReturn(collegeStudentList);

        assertIterableEquals(collegeStudentList, studentAndGradeServiceMock.getGradebook());

        MvcResult mvcResult = this.mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("firstname", request.getParameterValues("firstname"))
                        .param("lastname", request.getParameterValues("lastname"))
                        .param("emailAddress", request.getParameterValues("emailAddress")))
                .andExpect(status().isOk()).andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "index");

        // verify result if exist in the database
        CollegeStudent verifyStudent = studentDao.findByEmailAddress("maher.khe@guru2test_school.com");
        assertNotNull(verifyStudent, "Student should be found");
    }

    @Test
    public void deleteStudentHttpRequest() throws Exception {

        assertTrue(studentDao.findById(30).isPresent());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/delete/student/{id}", 30))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "index");

        // MAke sure student was deleted
        assertFalse(studentDao.findById(30).isPresent());
    }

    @Test
    public void deleteStudentHttpRequestErrorPage() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/delete/student/{id}", 0))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "error");
    }

	@DisplayName("student Information Http Request")
    @Test
    public void studentInformationHttpRequest() throws Exception {
        //Make sure student exists
        assertTrue(studentDao.findById(30).isPresent());

        // Perform HTTP request GET/studentInformation/{id}
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", 30))
                .andExpect(status().isOk()).andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "studentInformation");
    }

	@DisplayName("student Information Http Request Does not exist")
	@Test
	public void studentInformationHttpRequestDoesNotExist() throws Exception {
		//Make sure student does not exist
		assertFalse(studentDao.findById(0).isPresent());

		// Perform HTTP request GET/studentInformation/{id}
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}", 0))
				.andExpect(status().isOk()).andReturn();
		ModelAndView mav = mvcResult.getModelAndView();
		ModelAndViewAssert.assertViewName(mav, "error");
	}

    @Test
    public void createValidGradeHttpRequest() throws Exception {

        assertTrue(studentDao.findById(30).isPresent());
        GradebookCollegeStudent student = studentAndGradeService.studentInformation(30);

        // size of list grades is 1 expected value
        assertEquals(1, student.getStudentGrades().getMathGradeResults().size());

        //Perform HTTP request Post/grades Params: grade, gradeType, studentId
        MvcResult mvcResult = this.mockMvc.perform(post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .param("grade", "85.00")
                .param("gradeType", "math")
                .param("studentId","30"))
                .andExpect(status().isOk())
                .andReturn();
        ModelAndView mav = mvcResult.getModelAndView();
        ModelAndViewAssert.assertViewName(mav, "studentInformation");

        // studentId equals to 30
        student = studentAndGradeService.studentInformation(30);

        // size of grade result should have 2
        assertEquals(2, student.getStudentGrades().getMathGradeResults().size());
    }

    @Test
    public void createAValidGradeHttpRequestStudentDoesNotExistEmpyResponse() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("grade", "85.00")
                        .param("gradeType", "history")
                        .param("studentId", "0"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "error");
    }

    @Test
    public void createANonValidGradeHttpRequestGradeTypeDoesNotExistEmpyResponse() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/grades")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("grade", "85.00")
                        .param("gradeType", "literature")
                        .param("studentId", "30"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "error");
    }

    @Test
    public void deleteAValidGradeHttpRequest() throws Exception {

        Optional<MathGrade> mathGrade = mathGradesDao.findById(20);

        assertTrue(mathGrade.isPresent());

        // Delete the grade via HTTP request GET /grades/{id}/{gradeType}
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/grades/{id}/{gradeType}", 20, "math"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "studentInformation");

        // Confirm grade was really deleted by using assertFalse
        mathGrade = mathGradesDao.findById(20);
        assertFalse(mathGrade.isPresent());
    }

    @Test
    public void deleteAValidGradeHttpRequestStudentIdDoesNotExistEmptyResponse() throws Exception {

        Optional<MathGrade> mathGrade = mathGradesDao.findById(2);

        // confirm grade id does not exist
        assertFalse(mathGrade.isPresent());

        // Delete the grade via HTTP request GET /grades/{id}/{gradeType}
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/grades/{id}/{gradeType}", 2, "math"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "error");
    }
    @Test
    public void deleteANonValidGradeHttpRequest() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/grades/{id}/{gradeType}", 1, "literature"))
                .andExpect(status().isOk()).andReturn();

        ModelAndView mav = mvcResult.getModelAndView();

        ModelAndViewAssert.assertViewName(mav, "error");
    }


    @AfterEach
    public void setupAfterTransaction() {
        jdbcTemplate.execute(sqlDeleteStudent);
        jdbcTemplate.execute(sqlDeleteMathGrade);
        jdbcTemplate.execute(sqlDeleteScienceGrade);
        jdbcTemplate.execute(sqlDeleteHistoryGrade);
    }


}
