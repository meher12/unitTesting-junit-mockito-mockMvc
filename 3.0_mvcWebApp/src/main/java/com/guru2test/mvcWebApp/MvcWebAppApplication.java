package com.guru2test.mvcWebApp;

import com.guru2test.mvcWebApp.models.MathGrade;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class MvcWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcWebAppApplication.class, args);
	}

	@Bean
	@Scope(value="prototype")
	@Qualifier("mathGrades")
	MathGrade getGrade(){
		return new MathGrade();
	}

}
