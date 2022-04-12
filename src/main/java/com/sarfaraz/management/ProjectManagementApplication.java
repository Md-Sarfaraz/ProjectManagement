package com.sarfaraz.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication

public class ProjectManagementApplication {

	@Autowired
	private ObjectMapper objectMapper;

	public static void main(String[] args) {

		SpringApplication.run(ProjectManagementApplication.class, args);
	}

}
