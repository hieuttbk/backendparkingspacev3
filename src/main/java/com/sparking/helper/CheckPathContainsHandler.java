package com.sparking.helper;

import java.util.Arrays;

public class CheckPathContainsHandler {
    public static boolean checkPathContainsHandler(String pathRequest, String pathTag) {
        String[] arrayPath = pathRequest.split("/");

        boolean isContains = Arrays.toString(arrayPath).contains(pathTag);
        if (isContains) {
            return true;
        }
        return false;
    }
}
