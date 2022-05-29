package com.sarfaraz.management.service;

import static com.sarfaraz.management.model.selects.UserRoles.ROLE_PUBLIC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sarfaraz.management.exception.DatabaseUpdateException;
import com.sarfaraz.management.exception.UserInputException;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.model.dto.OnlyNameAndEmail;
import com.sarfaraz.management.model.dto.UserAllInfo;
import com.sarfaraz.management.model.dto.UserOnlyDTO;
import com.sarfaraz.management.model.selects.UserRoles;
import com.sarfaraz.management.repository.UserRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

	private final UserRepo userRepo;

	private final PasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> opt = userRepo.findByUsername(username);
		if (opt.isEmpty())
			throw new UsernameNotFoundException("User Not Found In the Database");
		User user = opt.get();
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		user.getRoles().forEach(role -> {
			// authorities.add(new SimpleGrantedAuthority(role.getName()));
			authorities.add(new SimpleGrantedAuthority(role.toString()));
		});
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}

	public List<User> listAll() {
		return userRepo.findAll();
	}

	public boolean save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		if (user.getRoles().isEmpty()) {
			user.addRole(ROLE_PUBLIC);
		}
		if (!user.getRoles().contains(ROLE_PUBLIC)) {
			user.addRole(ROLE_PUBLIC);
		}
		User u = userRepo.save(user);
		return u.getId() == user.getId();
	}

	public long updateUser(User user) {
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
		User u = userRepo.save(old);

		return u.getId();
	}

	public boolean updatePassword(Long id, String oldPass, String newPass) {// 3 param
		int result = 0;
		if (id != null && !newPass.isBlank()) {
			Optional<User> opt = userRepo.findById(id);
			opt.orElseThrow(() -> new DatabaseUpdateException("Can't find any Member with ID : " + id));
			User user = opt.get();

			if (encoder.matches(oldPass, user.getPassword())) {
				result = userRepo.updateUserPasswod(encoder.encode(newPass), user.getId());
				log.info("User New Password : " + newPass);
			}

			if (oldPass.equals(user.getPassword())) {
				result = userRepo.updateUserPasswod(newPass, user.getId());
				log.info("User New Password : " + newPass); // for testing and should be removed
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

	public Page<User> findByName(String name, int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));
		return userRepo.findByName(name, pageable);
	}

	public Optional<User> findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Transactional
	public boolean updateRole(long id, Set<UserRoles> roles) throws UserInputException {

		Optional<User> opt = userRepo.findById(id);
		if (opt.isEmpty())
			return false;

		User user = opt.get();
		user.getRoles().addAll(roles);
		userRepo.save(user);
		log.info("User Role Updated : {}", user.getRoles());

		return true;
	}

	public boolean addRole(long id, UserRoles role) {
		Optional<User> opt = userRepo.findById(id);
		if (opt.isEmpty())
			return false;
		opt.ifPresent(user -> {
			user.addRole(role);
			log.info(user.toString());
			// userRepo.save(user);
		});
		return true;
	}

	public boolean removeRole(long id, String role) {
		Optional<User> opt = userRepo.findById(id);
		if (opt.isEmpty())
			return false;
		opt.ifPresent(user -> {
			user.removeRole(role);
			userRepo.save(user);
		});
		return true;
	}

	public List<OnlyNameAndEmail> searchUser(String name) {
		log.info("Search Query\t:\t" + name);
		if (name.isBlank() || name.equalsIgnoreCase("_"))
			return new ArrayList<>();
		return userRepo.searchByName(name);
	}

	public Set<UserOnlyDTO> getAllbyProjectId(Long projectID) {
		return userRepo.getAllUserByProjectId(projectID);
	}

	public UserAllInfo getAllwithprojectandroles(Long uid) {
		return userRepo.getOneWithProjectAndRole(uid);
	}

	public Page<UserOnlyDTO> sortedByName(int page, int size) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("name"));
		Page<UserOnlyDTO> users = userRepo.findAllOnlyUser(pageable);
		return users;
	}

	public Page<UserOnlyDTO> sortedByfield(int page, int size, String sort) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sort));
		Page<UserOnlyDTO> users = userRepo.findAllOnlyUser(pageable);
		return users;
	}

}
