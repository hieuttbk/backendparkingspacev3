package com.hongdatchy.service_impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongdatchy.entities.data.User;
import com.hongdatchy.entities.social.GooglePojo;
import com.hongdatchy.entities.social.GooglePojoIdToken;
import com.hongdatchy.repository.UserRepo;

import com.hongdatchy.service.SocialService;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class GoogleService implements SocialService {

    @Autowired
    private UserRepo userRepo;

    @Value("${google.link.access_token}")
    private String linkAccessToken;

    @Value("${google.link.id_token}")
    private String linkIdToken;

    @Override
    public User getUserAccessToken(String token) throws IOException {
        String link = linkAccessToken + token; // tạo link api
        String response = Request.Get(link).execute().returnContent().asString(); // call api
        System.out.println("google access token response: " + response);
        ObjectMapper mapper = new ObjectMapper();
        GooglePojo pojo = mapper.readValue(response, GooglePojo.class); // map với entity
        String email = pojo.getEmail();
        User user = userRepo.findByEmail(email);
        if (user != null) return user;
        User newUser = buildUser(email);
        return userRepo.createAndUpdate(newUser);
    }

    @Override
    public User getUserIdToken(String token) throws IOException {
        String link = linkIdToken + token;
        String response = Request.Get(link).execute().returnContent().asString(); // call api
        System.out.println("google id token response: " + response);
        ObjectMapper mapper = new ObjectMapper();
        GooglePojoIdToken pojo = mapper.readValue(response, GooglePojoIdToken.class); // map với entity
        String email = pojo.getEmail();
        if(pojo.getEmail_verified().equals("true")){
            User user = userRepo.findByEmail(email);
            if (user != null){
                return user;
            }else {
                User newUser = buildUser(email);
                return userRepo.createAndUpdate(newUser);
            }
        }
        return null;
    }

    public User buildUser(String email){
        return User.builder()
                .id(null)
                .lastTimeAccess(null)
                .password("")
                .equipment("")
                .idNumber(0)
                .address("")
                .email(email)
                .address("")
                .phone("")
                .sex("")
                .image("")
                .birth(new Date())
                .build();
    }
}
