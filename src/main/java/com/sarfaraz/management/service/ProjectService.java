package com.sarfaraz.management.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sarfaraz.management.exception.ResourceNotFoundException;
import com.sarfaraz.management.model.Project;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.model.dto.ProjectOnlyDTO;
import com.sarfaraz.management.model.dto.TotalCounts;
import com.sarfaraz.management.repository.ProjectRepo;
import com.sarfaraz.management.repository.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepo repo;
	private final UserRepo userRepo;

	public Page<ProjectOnlyDTO> listAllSorted(int page, int size, String sortField, boolean ascending) {
		Pageable pageable = PageRequest.of(page - 1, size,
				ascending ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
		Page<ProjectOnlyDTO> projects = repo.findAllProjects(pageable);
		return projects;
	}

	public Page<ProjectOnlyDTO> search(String query, int page, int size, String sortField, boolean ascending) {
		Pageable pageable = PageRequest.of(page - 1, size,
				ascending ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
		Page<ProjectOnlyDTO> projects = repo.findByname(query, pageable);
		return projects;
	}

	public Page<ProjectOnlyDTO> search(String query, int page, int size, String sortField) {
		return search(query, page, size, sortField, true);
	}

	public TotalCounts totalCounts() {
		return repo.totalCounts();
	}



	public Optional<Project> getOne(Long id) {
		return repo.findById(id);
	}

	public boolean save(Project project) {
		if (project.getId() == null) {
			project.setCreated(LocalDate.now());
		} else {
			project.setUpdated(LocalDate.now());
		}
		log.info(project.toString());

		Project p = repo.save(project);
		return p.getId() == project.getId();
	}

	public boolean update(Project project) {
		// TODO should be opposite call
		return save(project);
	}

	public void delete(Long id) {
		repo.deleteById(id);
		log.warn("Project with id : {} is Deleted", id);
	}

	public long totalCount() {
		return repo.count();
	}

	public Set<ProjectOnlyDTO> getAllProjectsByUserId(Long id) {
		Set<ProjectOnlyDTO> projects = repo.findRelatedProjects(id);
		return projects;
	}

	@Transactional
	public boolean addUserToProject(Long projectId, Long userId) throws ResourceNotFoundException {
		Project project = repo.findById(projectId)
				.orElseThrow(() -> new ResourceNotFoundException("Not Found Project with ID : " + projectId));
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Not Found User with ID : " + userId));
		project.addUser(user);
		log.warn("User : {}, Project : {}", user.getId(), project.getId());
		return repo.save(project).getId() == projectId;
	}

	@Transactional
	public boolean removeUserFromProject(Long projectId, Long userId) {
		Project project = repo.findById(projectId)
				.orElseThrow(() -> new ResourceNotFoundException("Not Found Project with ID : " + projectId));
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("Not Found User with ID : " + userId));
		project.removeUser(user);
		log.warn("User : {}, Project : {}", user.getId(), project.getId());
		return repo.save(project).getId() == projectId;
	}

}