package com.sarfaraz.management.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sarfaraz.management.model.Project;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.model.dto.NameAndRole;
import com.sarfaraz.management.model.dto.ProjectOnlyDTO;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {

    enum Status {
        DEVELOPMENT, COMPLETED, HOLD, CANCELLED, ACTIVE;
    }

    @Modifying
    @Query(value = "update Project p  set p.users=null where p.id=:pid")
    int addUserToProject(Long pid);

    @Query(value = "select p from Project p")
    List<Project> findOnlyProjects();
    
	@Query(value = "select distinct p from Project p")
	Page<ProjectOnlyDTO> findAllOnlyProject(Pageable pageable);

    @Query(value = "select u from Project p join p.users u where p.id=:pid")
    List<User> getAllUserByProjectID(@Param("pid") Long pid);

    @Query(value = "select distinct new com.sarfaraz.management.model.dto.NameAndRole(u.id, u.name, u.email, r.id, r.name) " +
            "from User u left join u.roles r join u.projects p where p.id=:pid")
    Set<NameAndRole> getRelatedUserWithRoles(@Param("pid") Long id);


}
