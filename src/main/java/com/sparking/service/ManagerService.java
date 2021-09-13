package com.sparking.service;

import com.sparking.entities.data.Manager;
import com.sparking.entities.payloadReq.ChangePassForm;
import com.sparking.entities.payloadReq.LoginForm;
import com.sparking.entities.payloadReq.ManagerPayload;
import com.sparking.entities.payloadReq.VerifyResetPassPayload;

import java.util.List;

public interface ManagerService {

    Manager createAndUpdate(ManagerPayload managerPayload);

    boolean delete(int id);

    List<Manager> findAll();

    Manager login(LoginForm loginForm);

    Manager findById(int id);

    boolean changePass(ChangePassForm changePassForm, String email);

    boolean resetPass(String email);

    boolean verifyResetPass(VerifyResetPassPayload verifyResetPassPayload);

    // boolean changePass(ChangePassForm changePassForm, String email);
}
