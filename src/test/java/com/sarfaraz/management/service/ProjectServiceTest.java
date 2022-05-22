package com.sarfaraz.management.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.sarfaraz.management.model.Project;
import com.sarfaraz.management.repository.ProjectRepo;

@DataJpaTest
class ProjectServiceTest {
	@Autowired
	private ProjectRepo repo;
	private final long id = 247;

	
//	@Test
//	public void testFetchRelated() {
//
//		var data = repo.getUserByProject(id);
//
//		System.out.println(data);
//
//		assertTrue(true);
//	}

}
