package com.jot.JobOpportunity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/config/application.yml")
public class JobOpportunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobOpportunityApplication.class, args);
	}

}
