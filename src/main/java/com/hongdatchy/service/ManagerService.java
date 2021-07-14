package com.hongdatchy.service;

import com.hongdatchy.entities.data.Manager;
import com.hongdatchy.entities.payload.LoginForm;
import com.hongdatchy.entities.payload.ManagerPayload;

import java.util.List;

public interface ManagerService {

    Manager createAndUpdate(ManagerPayload managerPayload);

    boolean delete(int id);

    List<Manager> findAll();

    boolean login(LoginForm loginForm);

}
