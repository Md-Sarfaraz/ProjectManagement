package com.sarfaraz.management;

import javax.annotation.security.RolesAllowed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sarfaraz.management.repository.RoleRepo;

@SpringBootApplication
public class ProjectManagementApplication {

	public static void main(String[] args) {
		System.out.println(RoleRepo.Roles.ROLE_ADMIN.toString());
		System.out.println(RoleRepo.Roles.ROLE_ADMIN.name());
		SpringApplication.run(ProjectManagementApplication.class, args);
	}

}
