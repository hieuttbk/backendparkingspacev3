package com.sparking.repository;

import com.sparking.entities.data.Manager;
import com.sparking.entities.payload.LoginForm;

import java.util.List;

public interface ManagerRepo {

    Manager createAndUpdate(Manager manager);

    boolean delete(int id);

    List<Manager> findAll();

    Manager login(LoginForm loginForm);

    Manager findByEmail(String email);

    Manager findById(int id);
}
