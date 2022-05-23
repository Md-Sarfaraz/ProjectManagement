package com.sarfaraz.management.service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.sarfaraz.management.model.Project;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.model.dto.ProjectOnlyDTO;
import com.sarfaraz.management.repository.ProjectRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProjectService {

	private final ProjectRepo repo;

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

	public boolean addUserToProject(Long projectId, Long userId) {
		Optional<Project> opt = repo.findById(projectId);
		if (opt.isEmpty())
			return false;
		Project project = opt.get();
		project.addUser(new User(userId));
		return repo.save(project).getId() == projectId;
	}

	public boolean removeUserFromProject(Long projectId, Long userId) {
		Optional<Project> opt = repo.findById(projectId);
		if (opt.isEmpty())
			return false;
		Project project = opt.get();
		project.removeUser(new User(userId));
		return repo.save(project).getId() == projectId;
	}

}