package com.hongdatchy.service;

import com.hongdatchy.entities.data.Contract;
import com.hongdatchy.entities.data.User;
import com.hongdatchy.entities.payload.*;

import java.text.ParseException;
import java.util.List;

public interface UserService {

    User login(LoginForm loginForm);

    boolean register(RegisterForm registerForm);

    User createAndUpdate(UserPayload userPayload);

    boolean delete(int id);

    List<User> findAll();

    Contract book(BookPayload bookPayload, String email);

    boolean changePass(ChangePassForm changePassForm, String email);

    boolean verifyAccount(String mail, String code);

    Contract updateTime(TimeUpdateForm timeUpdateForm, String email) throws ParseException;

    List<Contract> getListContract(String email);

    Contract updateContractForUser(ContractPayload contractPayload, String email);

    User updateInfo(UserUpdateInfo userUpdateInfo, String email);

    boolean verifyResetPass(VerifyResetPassPayload verifyResetPassPayload);

    boolean resetPass(String email);

    Contract park(ParkPayload parkPayload, String email);
}
