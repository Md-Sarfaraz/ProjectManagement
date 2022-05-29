package com.sarfaraz.management.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sarfaraz.management.model.Ticket;
import com.sarfaraz.management.model.dto.TicketListDTO;
import com.sarfaraz.management.model.dto.TicketListProjectDTO;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {

//	@Query(value = "select t.id as id, t.name as name, t.status as status, t.priority as priority, "
//			+ " t.type as type, pr as project, au as assignedUser, su as submitter"
//			+ " from Ticket t join fetch t.project pr join fetch t.assignedUser au join fetch t.submitter su WHERE pr.id=:id")
//	List<TicketListDTO> getAllByProjectId(@Param("id") Long id);

	@Query(value = "select t"
			// + " pr.name as project.id, pr.name as project.name,"
			// + " au.id as user.id, au.name as user.name,"
			// + " su.id as user.id, su.name as user.name"
			+ " from Ticket t  WHERE t.project.id=:id")
	Page<TicketListDTO> getAllByProjectId(@Param("id") Long id, Pageable pageable);

	@Query(value = "SELECT DISTINCT t FROM Ticket t WHERE t.submitter.id=:uid OR t.assignedUser.id=:uid ")
	Set<TicketListDTO> findAllByUserId(@Param("uid") Long uid);

	@Query(value = "select t from Ticket t")
	Page<TicketListDTO> getAllTickets(Pageable pagable);

	// @Query(value = "select t from Ticket t where t.id=:id")
	@Query(value = "select t from Ticket t where t.id=:id")
	Optional<TicketListDTO> findOne(Long id);

	@Modifying
	@Query(value = "UPDATE Ticket t SET t.assignedUser.id=:uid WHERE t.id=:tid")
	Integer assignNewUser(Long tid, Long uid);

	// TODO below has some problems, requires testing...
	@Modifying
	@Query(value = "UPDATE Ticket t SET t.assignedUser.id=null WHERE t.id=:id")
	Integer unassignUser(Long id);

//	@Query(value = "select t.id as id, t.name as name, t.status as status, t.priority as priority, "
//			+ "t.type as type, pr as project, au as assignedUser, su as submitter"
//			+ " from Ticket t join t.type ty join t.project pr join t.assignedUser au join t.submitter su")
//	List<TicketListDTO> getAllDTO(); // error

	@Query(value = "select t.id as id, t.name as name, t.status as status, t.type as type, "
			+ "t.assignedUser as assignedUser from Ticket t where t.project.id=:id")
	List<TicketListDTO> getRelatedTickets(@Param("id") Long id);

}
