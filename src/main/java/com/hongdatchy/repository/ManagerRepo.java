package com.hongdatchy.repository;

import com.hongdatchy.entities.data.Manager;
import com.hongdatchy.entities.data.User;
import com.hongdatchy.entities.payload.LoginForm;
import com.hongdatchy.entities.payload.RegisterForm;

import java.util.List;

public interface ManagerRepo {

    Manager createAndUpdate(Manager manager);

    boolean delete(int id);

    List<Manager> findAll();

    boolean login(LoginForm loginForm);

    Manager findByEmail(String email);
}
