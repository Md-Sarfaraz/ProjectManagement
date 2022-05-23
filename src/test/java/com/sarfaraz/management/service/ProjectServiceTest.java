package com.sarfaraz.management.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.sarfaraz.management.model.Project;
import com.sarfaraz.management.repository.ProjectRepo;

import lombok.RequiredArgsConstructor;


class ProjectServiceTest {
	private Project project = new Project();

	@Test
	public void testFetchRelated() {
		project.setId(233l);
		project.setName("TestingApp");
		project.setDetail("this is a testing app");
		project.setCreated(LocalDate.of(2022, 4, 21));
		project.setLastDate(LocalDate.of(2022, 9, 4));

		if (!(project.getId() == null || project.getId() == 0))
			project.setUpdated(LocalDate.now());
		else
			project.setCreated(LocalDate.now());

		System.out.println(project);

		assertTrue(true);
	}

}
