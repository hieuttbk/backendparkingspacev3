package com.hongdatchy.service_impl;

import com.hongdatchy.entities.payload.LoginForm;
import com.hongdatchy.repository.AdminRepo;
import com.hongdatchy.repository.BlackListRepo;
import com.hongdatchy.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService_Impl implements AdminService {

    @Autowired
    AdminRepo adminRepo;

    @Autowired
    BlackListRepo blackListRepo;

    @Override
    public boolean login(LoginForm loginForm) {
        return adminRepo.login(loginForm);
    }

}
