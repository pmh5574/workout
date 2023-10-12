package com.calendar.workout;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
public class WorkoutApplication {

	private static final String PROPERTIES = "spring.config.location=classpath:/oauth.yml";

	public static void main(String[] args) {
		SpringApplication.run(WorkoutApplication.class, args);
	}

}
