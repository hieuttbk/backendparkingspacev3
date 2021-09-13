package com.sparking.service_impl;

import com.sparking.entities.data.Manager;
import com.sparking.entities.data.User;
import com.sparking.entities.payloadReq.ChangePassForm;
import com.sparking.entities.payloadReq.LoginForm;
import com.sparking.entities.payloadReq.ManagerPayload;
import com.sparking.entities.payloadReq.VerifyResetPassPayload;
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

    @Override
    public boolean changePass(ChangePassForm changePassForm, String email) {

            Manager man = managerRepo.findByEmail(email);
            return man != null && managerRepo.changePass(changePassForm, man);
        }

    @Override
    public boolean resetPass(String email) {
        return managerRepo.resetPass(email);
    }

    @Override
    public boolean verifyResetPass(VerifyResetPassPayload verifyResetPassPayload) {
       return managerRepo.verifyResetPass(verifyResetPassPayload);
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
