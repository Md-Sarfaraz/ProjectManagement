package com.sarfaraz.management.service;

import com.sarfaraz.management.exception.DatabaseUpdateException;
import com.sarfaraz.management.model.Role;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.model.dto.NameAndRole;
import com.sarfaraz.management.model.dto.OnlyNameAndEmail;
import com.sarfaraz.management.repository.RoleRepo;
import com.sarfaraz.management.repository.UserRepo;
import static com.sarfaraz.management.repository.RoleRepo.Roles.ROLE_PUBLIC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	private final UserRepo userRepo;
	private final RoleRepo roleRepo;
	// private final PasswordEncoder encoder;

	@Autowired
	public UserService(UserRepo userRepo, RoleRepo roleRepo) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		// this.encoder = encoder;
	}

	@Transactional
	public List<User> listAll() {
		return userRepo.findAll();
	}

	@Transactional
	public Set<NameAndRole> listAllWithRoles() {
		return userRepo.getAllWithRoles();
	}

	public void save(User user) {
		// user.setPassword(encoder.encode(user.getPassword()));
		user.setPassword(user.getPassword());
		if (user.getRoles().isEmpty()) {
			Optional<Role> roleOP = roleRepo.findByName(ROLE_PUBLIC.name());
			user.addRole(roleOP.orElse(new Role(ROLE_PUBLIC.name(), ROLE_PUBLIC.getDetails())));
		}
		userRepo.save(user);
	}

	public void updateUser(User user) {
		User old = userRepo.getById(user.getId());
		if (!user.getEmail().isBlank())
			old.setEmail(user.getEmail());
		if (!user.getName().isBlank())
			old.setName(user.getName());
		if (!user.getMobile().isBlank())
			old.setMobile(user.getMobile());
		if (!user.getAddress().isBlank())
			old.setAddress(user.getAddress());
//        if (!user.getImagePath().isBlank()) old.setImagePath(user.getImagePath());
		if (!user.getDob().toString().isBlank())
			old.setDob(user.getDob());
		userRepo.save(old);
	}

	@Transactional
	public boolean updatePassword(Long id, String oldPass, String newPass) {// 3 param
		int result = 0;
		if (id != null && !newPass.isBlank()) {
			Optional<User> opt = userRepo.findById(id);
			opt.orElseThrow(() -> new DatabaseUpdateException("Can't find any Member with ID : " + id));
			User user = opt.get();
			/*
			 * if (encoder.matches(oldPass, user.getPassword())) { result =
			 * userRepo.updateUserPasswod(encoder.encode(newPass), user.getId());
			 * log.info("User New Password : " + newPass); }
			 */
			if (oldPass == user.getPassword()) {
				result = userRepo.updateUserPasswod(newPass, user.getId());
				log.info("User New Password : " + newPass);
			}
		}
		return result > 0;
	}

	public Optional<User> getOne(Long id) {
		return userRepo.findById(id);
	}

	public void delete(Long id) {
		userRepo.deleteById(id);
		log.info(String.format("User with ID : %s deleted successfully.", id));
	}

	public Optional<User> findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	public Optional<User> findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Transactional
	public void updateRole(String name, String email, Set<Role> roles) {
		Optional<User> opt = userRepo.findByNameAndEmail(name, email);
		opt.ifPresent(user -> {
			user.getRoles().clear();
			roles.forEach(r -> {
				Optional<Role> opRole = roleRepo.findByName(r.getName());
				user.addRole(opRole.orElse(r));
			});
			userRepo.save(user);
		});
	}

	public List<OnlyNameAndEmail> searchUser(String name) {
		log.info("Search Query\t:\t" + name);
		if (name.isBlank() || name.equalsIgnoreCase("_"))
			return new ArrayList<>();
		return userRepo.searchByName(name);
	}

	public List<User> getAllbyProjectID(Long projectID) {
		return userRepo.getAllByProjectID(projectID);
	}

	public Page<User> sortedByName(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("name"));
		Page<User> users = userRepo.findAll(pageable);
		return users;
	}
}
