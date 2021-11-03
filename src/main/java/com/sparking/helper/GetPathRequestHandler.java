package com.sparking.helper;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class GetPathRequestHandler {
    public static String getPathRequest() {
        UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        String requestValue = builder.buildAndExpand().getPath();
        return requestValue;
    }
}
