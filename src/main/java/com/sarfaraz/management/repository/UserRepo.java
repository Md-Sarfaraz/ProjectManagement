package com.sarfaraz.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sarfaraz.management.model.User;
import com.sarfaraz.management.model.dto.NameAndRole;
import com.sarfaraz.management.model.dto.NameWithRole;
import com.sarfaraz.management.model.dto.OnlyNameAndEmail;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "select u from User u join fetch u.roles where u.username=:username")
    Optional<User> findByUsername(@Param("username") String username);

    @Query(value = "select u from User u join fetch u.roles where u.email=:email")
    Optional<User> findByEmail(@Param("email") String email);

    Optional<User> findByNameAndEmail(String name, String email);

    @Query(value = "select distinct new com.bug.tracker.model.dto.NameAndRole(u.id, u.name, u.email, r.id, r.name) " +
            "from User u left join u.roles r")
    Set<NameAndRole> getAllWithRoles();

    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    int updateUserPasswod(@Param("password") String password, @Param("id") Long id);

    @Query(value = "select u.id as id, u.name as name, u.email as email from User u where u.name like %?1%")
    List<OnlyNameAndEmail> searchByName(String name);

    @Query(value = "select u.id as id, u.name as name, u.email as email from User u")
    List<OnlyNameAndEmail> getOnlyNameAndEmail();


    @Query(value = "select distinct u from User u left join fetch u.roles")
    List<NameWithRole> findAllNamesWithRoles();

    @Query(value = "select u from User u join fetch u.roles r join u.project p where p.id=:pid")
    List<User> getAllByProjectID(@Param("pid") Long id);


    //TODO work in progress below

    @Query(value = "select distinct new com.bug.tracker.model.dto.NameAndRole(u.id, u.name, u.email, r.id, r.name) " +
            "from User u left join u.roles r join u.project p where p.id=:pid")
    Set<NameAndRole> getAllRelated(@Param("pid") Long id);

    @Query(value = "select distinct  u.id as id, u.name as name, u.email as email, r as roles from User u left join  u.roles r join u.project p where p.id=:pid")
    List<Object[]> findor(@Param("pid") Long pid);

    @Query(value = "select u.id as id, u.name as name, u.email as email," +
            "r as roles from User u left join u.roles r " +
            "join u.project p where p.id=:pid")
    Set<NameWithRole> namedoc(@Param("pid") Long pid);

}
