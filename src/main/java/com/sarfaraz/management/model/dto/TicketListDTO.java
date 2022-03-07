package com.sarfaraz.management.model.dto;

public interface TicketListDTO {

    Long getId();

    String getName();

    String getStatus();

    String getPriority();

    TypeDTO getType();

    ProjectDTO getProject();

    UserDTO getAssignedUser();

    UserDTO getSubmitter();

    interface TypeDTO {

        Long getId();

        String getName();
    }

    interface ProjectDTO {
        Long getId();

        String getName();
    }

    interface UserDTO {
        Long getId();

        String getName();
    }
}
