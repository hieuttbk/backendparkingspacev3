package com.sparking.service_impl;

import com.sparking.entities.data.Manager;
import com.sparking.entities.payload.LoginForm;
import com.sparking.entities.payload.ManagerPayload;
import com.sparking.repository.BlackListRepo;
import com.sparking.repository.ManagerRepo;
import com.sparking.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManagerService_Impl implements ManagerService {

    @Autowired
    ManagerRepo managerRepo;

    @Autowired
    BlackListRepo blackListRepo;

    @Override
    public Manager createAndUpdate(ManagerPayload  managerPayload) {
        System.out.println("DEBUG create man" + payload2data(managerPayload));
        return managerRepo.createAndUpdate(payload2data(managerPayload));
    }

    @Override
    public boolean delete(int id) {
        return managerRepo.delete(id);
    }

    @Override
    public List<Manager> findAll() {
        return managerRepo.findAll();
    }

    @Override
    public Manager login(LoginForm loginForm) {
        return managerRepo.login(loginForm);
    }

    @Override
    public Manager findById(int id) {
        return managerRepo.findById(id);
    }

    public Manager payload2data(ManagerPayload managerPayload){
        return Manager.builder()
                .id(managerPayload.getId())
                .email(managerPayload.getEmail())
                .acp(managerPayload.getAcp())
                .pass(managerPayload.getPass())
                .lastTimeAccess(null)
                .build();
    }
}
