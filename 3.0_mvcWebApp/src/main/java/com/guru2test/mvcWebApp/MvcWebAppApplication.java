package com.guru2test.mvcWebApp;

import com.guru2test.mvcWebApp.models.Grade;
import com.guru2test.mvcWebApp.models.HistoryGrade;
import com.guru2test.mvcWebApp.models.MathGrade;
import com.guru2test.mvcWebApp.models.ScienceGrade;
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

	@Bean
	@Scope(value="prototype")
	@Qualifier("scienceGrade")
	ScienceGrade getScienceGrade(){
		return new ScienceGrade();
	}

	@Bean
	@Scope(value="prototype")
	@Qualifier("historyGrade")
	HistoryGrade getHistoryGrade(){
		return new HistoryGrade();
	}


}
