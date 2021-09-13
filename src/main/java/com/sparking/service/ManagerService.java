package com.sparking.service;

import com.sparking.entities.data.Manager;
import com.sparking.entities.payload.LoginForm;
import com.sparking.entities.payload.ManagerPayload;

import java.util.List;

public interface ManagerService {

    Manager createAndUpdate(ManagerPayload managerPayload);

    boolean delete(int id);

    List<Manager> findAll();

    Manager login(LoginForm loginForm);

    Manager findById(int id);

}
