package com.sparking.helper;

import java.util.Arrays;

public class CheckPathContainsHandler {
    public static boolean checkPathContainsHandler(String pathRequest) {
        String[] arrayPath = pathRequest.split("/");
        String pathTag = "tags";

        boolean isContains = Arrays.toString(arrayPath).contains(pathTag);
        if (isContains) {
            return true;
        }
        return false;
    }
}
