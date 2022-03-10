package com.sarfaraz.management.repository;

import com.sarfaraz.management.model.dto.TicketListDTO;
import com.sarfaraz.management.model.dto.TicketListProjectDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sarfaraz.management.model.Ticket;

import java.util.List;

public interface TicketRepo {

	@Query(value = "select t from Ticket t join t.assignedUser au join t.submitter su join fetch t.project p")
	List<Ticket> getWithAllRelated();

	@Query(value = "select t.id as id, t.name as name, t.status as status, t.priority as priority, "
			+ "t.type as type, pr as project, au as assignedUser, su as submitter"
			+ " from Ticket t join t.type ty join t.project pr join t.assignedUser au join t.submitter su")
	List<TicketListDTO> getAllDTO();

	@Query(value = "select t.id as id, t.name as name, t.status as status, t.type as type, "
			+ "t.assignedUser as assignedUser from Ticket t where t.project.id=:id")
	List<TicketListProjectDTO> getRelatedTickets(@Param("id") Long id);

	enum Types {
		ISSUE("Bug/Issue"), FEATURE("Feature Request"), PERFORMANCE("Performance Issue"), OTHERS("Others");

		private String displayName;

		Types(String string) {
			displayName = string;
		}

		public String getName() {
			return displayName;
		}

	}

}
