package com.hongdatchy.service;

import com.hongdatchy.entities.data.User;

import java.io.IOException;

public interface SocialService {

    User getUserAccessToken(String token) throws Exception;

    User getUserIdToken(String token) throws IOException;

}
