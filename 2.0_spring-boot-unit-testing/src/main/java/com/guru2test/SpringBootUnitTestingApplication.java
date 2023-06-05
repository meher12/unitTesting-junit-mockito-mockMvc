package com.guru2test;

import com.guru2test.models.CollegeStudent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class SpringBootUnitTestingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootUnitTestingApplication.class, args);
	}

	@Bean(name = "collegeStudent")
	@Scope(value = "prototype")
	CollegeStudent getCollegeStudent() {return new CollegeStudent();}
}
