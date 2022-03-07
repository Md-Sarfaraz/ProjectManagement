package com.sarfaraz.management.model.dto;

import org.springframework.beans.factory.annotation.Value;

public interface TicketListProjectDTO {

    Long getId();

    String getName();

    String getStatus();

    @Value("#{target.type.name}")
    String getType();

    @Value("#{target.assignedUser.name}")
    String getAssignedUser();

}
