package com.sparking.helper;

import java.util.Arrays;

public class FormatDateFromNewsTag {
    public static String FormatDate(String date) {
        String array[] = date.split("/");
        return String.join("-", array[0], array[1], array[2]);
    }
}
