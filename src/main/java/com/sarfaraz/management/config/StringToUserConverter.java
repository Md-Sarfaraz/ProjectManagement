package com.sarfaraz.management.config;

import com.sarfaraz.management.model.User;
import org.springframework.core.convert.converter.Converter;

public class StringToUserConverter implements Converter<String, User> {
    @Override
    public User convert(String source) {
        if (!source.isBlank())
            return new User(Long.parseLong(source));

            // TODO  Must Be Changed
        else return new User();
    }
}
