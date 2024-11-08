package com.tecnocampus.courseProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CourseProjectApplication{

	public static void main(String[] args) {
		SpringApplication.run(CourseProjectApplication.class, args);
	}

}
