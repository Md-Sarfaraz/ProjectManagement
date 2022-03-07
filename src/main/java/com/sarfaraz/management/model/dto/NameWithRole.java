package com.sarfaraz.management.model.dto;

import java.util.List;

public interface NameWithRole {

    Long getId();

    String getEmail();

    String getName();

    List<RoleName> getRoles();

    interface RoleName {

        String getId();

        String getName();

    }


}
