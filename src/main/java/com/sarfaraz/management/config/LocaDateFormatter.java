package com.sarfaraz.management.config;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class LocaDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        if (text.isBlank()) return null;
        if (text.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})"))
            return LocalDate.parse(text, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        if (text.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})"))
            return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        else return null;
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
