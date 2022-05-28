package com.sarfaraz.management.repository;

import com.sarfaraz.management.model.dto.OnlyNameAndEmail;
import com.sarfaraz.management.model.dto.UserAllInfo;
import com.sarfaraz.management.model.dto.UserOnlyDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sarfaraz.management.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	@Query(value = "select distinct u.id as id, u.name as name, u.dob as dob, "
			+ " u.address as address, u.mobile as mobile, u.username as username, u.email as email"
			+ " from User u join u.projects p where p.id=:pid")
	Set<UserOnlyDTO> getAllUserByProjectId(@Param("pid") Long pid);
	////////////////////////////////////////////

	@Query(value = "select u from User u left join fetch u.roles where u.username=:username")
	Optional<User> findByUsername(@Param("username") String username);

	@Query(value = "select u from User u where lower(concat(u.name, u.email, u.mobile, u.username)) like lower(concat('%',?1,'%'))")
	Page<User> findByName(String name, Pageable pageable);

	@Query(value = "select u from User u left join fetch u.roles where u.email=:email")
	Optional<User> findByEmail(@Param("email") String email);

	Optional<User> findByNameAndEmail(String name, String email);

	@Modifying
	@Query("update User u set u.password = :password where u.id = :id")
	int updateUserPasswod(@Param("password") String password, @Param("id") Long id);

	@Query(value = "select u.id as id, u.name as name, u.email as email from User u where u.name like %?1%")
	List<OnlyNameAndEmail> searchByName(String name);

	@Query(value = "select u.id as id, u.name as name, u.email as email from User u")
	List<OnlyNameAndEmail> getOnlyNameAndEmail();

	@Query(value = "select distinct u"
			+ " from User u left join fetch u.roles r left join fetch u.projects p where u.id=:uid")
	UserAllInfo getOneWithProjectAndRole(@Param("uid") Long uid);

	// TODO work in progress below

	@Query(value = "select distinct u from User u")
	Page<UserOnlyDTO> findAllOnlyUser(Pageable pageable);

}
