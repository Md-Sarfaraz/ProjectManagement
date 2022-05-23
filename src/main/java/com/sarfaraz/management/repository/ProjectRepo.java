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
import com.sarfaraz.management.model.Ticket;
import com.sarfaraz.management.model.User;
import com.sarfaraz.management.model.dto.ProjectOnlyDTO;
import com.sarfaraz.management.model.dto.TicketListDTO;
import com.sarfaraz.management.model.dto.TotalCounts;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Long> {



	@Modifying
	@Query(value = "update Project p  set p.users=null where p.id=:pid")
	int addUserToProject(Long pid);

	@Query(value = "select p from Project p")
	List<Project> findOnlyProjects();

	@Query(value = "select distinct p from Project p where lower(p.name) like lower(concat('%',?1,'%'))")
	Page<ProjectOnlyDTO> findByname(String name, Pageable pageable);

	@Query(value = "select distinct p from Project p")
	Page<ProjectOnlyDTO> findAllOnlyProject(Pageable pageable);

	@Query(value = "select p from Project p  join p.users u where u.id=:uid")
	List<ProjectOnlyDTO> findAllByUser(Long uid);

	// Fetch All Project Related To User (Direct or through Tickets)
	@Query(value = "select distinct p.id as id, p.name as name, p.detail as detail, p.lastDate as lastDate, "
			+ "p.created as created, p.updated as updated, p.status as status"
			+ " from Ticket t join  t.project p join p.users u where t.submitter.id=:uid or t.assignedUser.id=:uid or u.id=:uid")
	Set<ProjectOnlyDTO> findRelatedProjects(Long uid);

// TODO : Get the Counts of All Tables Total Data : Not Done
//	@Query(value = "SELECT p FROM ")
//	TotalCounts getTotalCounts();



	@Query(value = "SELECT t FROM Ticket t WHERE t.project.id=:pid")
	List<Ticket> getUserByProject(@Param("pid") Long pid);
//	Object[] getUserByProject(@Param("pid") Long pid);

	
	
	enum Status {
		DEVELOPMENT, COMPLETED, HOLD, CANCELLED, ACTIVE;
	}
}
