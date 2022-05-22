package com.sarfaraz.management.serviceImpl;

import java.util.Set;

import org.springframework.data.domain.Page;

import com.sarfaraz.management.model.Project;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.model.dto.ProjectOnlyDTO;
import com.sarfaraz.management.service.ProjectService;

public class ProjectServiceImple implements ProjectService {

	@Override
	public Page<ProjectOnlyDTO> getAllSortedByFields(int page, int size, String sort, boolean ascending) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ProjectOnlyDTO> searchByField(String query, int page, int size, String fieldName, boolean ascending) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<ProjectOnlyDTO> searchByField(String query, int page, int size, boolean ascending) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Project getOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(Project project) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Project project) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<User> getAllUserByProjectID(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addUserToProject(Long projectId, Long userID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeUserFromProject(Long projectId, Long userID) {
		// TODO Auto-generated method stub
		return false;
	}

}
