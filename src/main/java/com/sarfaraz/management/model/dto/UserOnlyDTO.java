package com.sarfaraz.management.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public interface UserOnlyDTO {

    Long getId();

    String getName();

    String getEmail();

    String getUsername();

    String getPassword();

    LocalDate getDob();

    String getMobile();

    String getAddress();

    boolean getActive();

    String getImagePath();
}
