package com.guru2test;

import com.guru2test.dao.ApplicationDao;
import com.guru2test.models.CollegeStudent;
import com.guru2test.service.ApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class SpringBootUnitTestingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootUnitTestingApplication.class, args);
	}

	@Bean(name = "applicationExample")
	ApplicationService getApplicationService() {
		return new ApplicationService();
	}


	@Bean(name = "applicationDao")
	ApplicationDao getApplicationDao() {
		return new ApplicationDao();
	}

	@Bean(name = "collegeStudent")
	@Scope(value = "prototype")
	CollegeStudent getCollegeStudent() {return new CollegeStudent();}
}
