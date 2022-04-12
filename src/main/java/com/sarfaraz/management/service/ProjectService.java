package com.sarfaraz.management.service;

import com.sarfaraz.management.model.Project;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.model.dto.NameAndRole;
import com.sarfaraz.management.model.dto.ProjectOnlyDTO;
import com.sarfaraz.management.model.dto.UserOnlyDTO;
import com.sarfaraz.management.repository.ProjectRepo;
import com.sarfaraz.management.repository.UserRepo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectService {

	private final ProjectRepo repo;
	private final UserRepo userRepo;
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	public ProjectService(ProjectRepo repo, UserRepo userRepo) {

		this.repo = repo;
		this.userRepo = userRepo;
	}

	public List<Project> listAll() {
		return repo.findOnlyProjects();
	}

	public Long save(Project project) {
		if (project.getId() == null) {
			project.setCreated(LocalDate.now());
			log.info(project.toString());
		}
		if (!(project.getId() == null || project.getId() == 0))
			project.setUpdated(LocalDate.now());
		Project p = repo.save(project);
		return p.getId();

	}

	@Transactional
	public Optional<Project> getOne(Long id) {
		return repo.findById(id);
	}

	@Transactional
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Transactional
	public void addUserToProject(Long projectID, Long userID) {
		Optional<Project> opt = repo.findById(projectID);
		opt.ifPresent(project -> {
			User user = userRepo.getById(userID);
			project.addUser(user);
			repo.save(project);
			log.info("Adding User Done");
		});

	}

	@Transactional
	public void removeUserFromProject(Long projectID, Long userID) {
		Optional<Project> opt = repo.findById(projectID);
		opt.ifPresent(project -> {
			User user = userRepo.getById(userID);
			user.getProjects().remove(project);
			project.getUsers().remove(user);
			repo.save(project);
			log.info("Adding User Done");
		});
	}
	/*
	 * @Transactional public JSONArray getAllUserByProjectID(Long id) throws
	 * JSONException { List<User> users = repo.getAllUserByProjectID(id); JSONArray
	 * array = new JSONArray(); for (User u : users) { JSONObject ob = new
	 * JSONObject(); ob.put("id", u.getId()); ob.put("name", u.getName());
	 * ob.put("email", u.getEmail()); JSONArray rolArray = new JSONArray();
	 * u.getRoles().forEach(role -> { try { JSONObject object = new JSONObject();
	 * object.put("id", role.getId()); object.put("name", role.getName());
	 * object.put("detail", role.getDetail()); rolArray.put(object); } catch
	 * (JSONException e) { e.printStackTrace(); } }); ob.put("roles", rolArray);
	 * array.put(ob); } return array; }
	 */

	@Transactional
	public Set<User> getAllUserByProjectID(Long id) throws JSONException {
		Set<User> users = repo.getAllUserByProjectID(id);
		return users;
	}

	@Transactional
	public Set<NameAndRole> getAllRelatedUsers(Long id) {
		return repo.getRelatedUserWithRoles(id);
	}

	public Page<ProjectOnlyDTO> sortedByField(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));
		Page<ProjectOnlyDTO> projects = repo.findAllOnlyProject(pageable);
		return projects;
	}

	public Page<ProjectOnlyDTO> searchByField(String name, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));
		Page<ProjectOnlyDTO> projects = repo.findByname(name, pageable);
		return projects;
	}
}
