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

	@Query(value = "select u from Project p join p.users u where p.id=:pid")
	Set<User> getAllUserByProjectId(@Param("pid") Long pid);
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

	@Query(value = "select distinct u from User u join fetch u.roles r join u.projects p where p.id=:pid")
	Set<User> getAllByProjectId(@Param("pid") Long id);

	@Query(value = "select distinct u"
			+ " from User u left join fetch u.roles r left join fetch u.projects p where u.id=:uid")
	UserAllInfo getOneWithProjectAndRole(@Param("uid") Long uid);

	// TODO work in progress below

	@Query(value = "select distinct u from User u")
	Page<UserOnlyDTO> findAllOnlyUser(Pageable pageable);

//	@Query(value = "select distinct new com.sarfaraz.management.model.dto.NameAndRole(u.id, u.name, u.email, r.id, r.name) "
//			+ "from User u left join u.roles r join u.projects p where p.id=:pid")
//	Set<NameAndRole> getAllRelated(@Param("pid") Long id);

	enum Roles {
		ROLE_ADMIN("Admin can access to all EndPoints"), ROLE_MANAGER("Manager can manage users, projects and tickets"),
		ROLE_TESTER("Tester can raise a bug or request for features, access all the tickets"
				+ " of projects which is issued by manager"),
		ROLE_DEVELOPER("Developer can accept and deal with tickets of the issued projects"),
		ROLE_PUBLIC("Public can Only learn About the project and can't interact with databases");

		private String desc;

		Roles(String desc) {
			this.desc = desc;

		}

		public String getDetails() {
			return this.desc;
		}
	}
}
