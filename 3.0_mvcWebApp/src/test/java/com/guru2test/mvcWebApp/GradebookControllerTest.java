package com.guru2test.mvcWebApp;

import com.guru2test.mvcWebApp.service.StudentAndGradeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

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

	@Autowired
	private StudentAndGradeService studentAndGradeServiceMock;

	@BeforeEach
	public void setupDataBase(){
		jdbcTemplate.execute("insert into student(id, firstname, lastname, email_address)" +
				"values (1, 'Eric', 'Roby', 'eric.roby@guru2test_school.com')");
	}

	@AfterEach
	public void setupAfterTransaction(){
		jdbcTemplate.execute("DELETE FROM student");
	}


}
