package com.sarfaraz.management.service;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.sarfaraz.management.model.Project;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.model.dto.ProjectOnlyDTO;

@Repository
public interface ProjectService {

	public Page<ProjectOnlyDTO> getAllSortedByFields(int page, int size, String sort, boolean ascending);

	public Page<ProjectOnlyDTO> searchByField(String query, int page, int size, String fieldName, boolean ascending);

	public Page<ProjectOnlyDTO> searchByField(String query, int page, int size, boolean ascending);

	public Project getOne(Long id);

	public boolean save(Project project);

	public boolean update(Project project);

	public boolean delete(Long id);

	public Set<User> getAllUserByProjectID(Long id);

	public boolean addUserToProject(Long projectId, Long userID);

	public boolean removeUserFromProject(Long projectId, Long userID);

}