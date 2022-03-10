package com.sarfaraz.management.config;

import com.sarfaraz.management.model.Project;
import com.sarfaraz.management.model.User;
import org.springframework.core.convert.converter.Converter;

public class StringToProjectConverter implements Converter<String, Project> {
    @Override
    public Project convert(String source) {
        if (!source.isBlank())
            return new Project(Long.parseLong(source));

            // TODO  Must Be Changed
        else return new Project();
    }
}
