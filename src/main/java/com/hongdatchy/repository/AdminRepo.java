package com.hongdatchy.repository;

import com.hongdatchy.entities.data.Admin;
import com.hongdatchy.entities.data.User;
import com.hongdatchy.entities.payload.LoginForm;

public interface AdminRepo {
    boolean login(LoginForm loginForm);
    Admin findByEmail(String email);
}
