package com.sarfaraz.management.util;

import org.json.JSONArray;

import java.time.LocalDate;

public class Helper {

    public static JSONArray getArrayofDate(LocalDate date) {
        JSONArray update = new JSONArray();
        if (date == null) return null;
        update.put(date.getYear());
        update.put(date.getMonthValue());
        update.put(date.getDayOfMonth());
        return update;
    }
}
