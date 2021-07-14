package com.hongdatchy.controller;

import com.hongdatchy.entities.data.User;
import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.entities.payload.LoginForm;
import com.hongdatchy.entities.payload.UserLoginPayload;
import com.hongdatchy.repository.AdminRepo;
import com.hongdatchy.repository.ManagerRepo;
import com.hongdatchy.repository.UserRepo;
import com.hongdatchy.security.JWTService;
import com.hongdatchy.service.AdminService;
import com.hongdatchy.service.ManagerService;
import com.hongdatchy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class CommonController {

    @Autowired
    UserService userService;

    @Autowired
    ManagerService managerService;

    @Autowired
    AdminService adminService;

    @Autowired
    JWTService jwtService;

    @Autowired
    AdminRepo adminRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ManagerRepo managerRepo;

    @PostMapping("api/public/login")
    public ResponseEntity<Object> login(@RequestBody LoginForm loginForm) throws Exception {
        User user =  userService.login(loginForm);
        if(user !=null){
            return ResponseEntity.ok(MyResponse
                    .loginSuccess("user",new UserLoginPayload(jwtService.getToken(loginForm.getEmail()),user)));
        }else if(managerService.login(loginForm)){
            return ResponseEntity.ok(MyResponse.loginSuccess("manager",jwtService.getToken(loginForm.getEmail())));
        }else if(adminService.login(loginForm)){
            return ResponseEntity.ok(MyResponse.loginSuccess("admin",jwtService.getToken(loginForm.getEmail())));
        }
        return ResponseEntity.ok(MyResponse.fail("wrong email or password"));
    }
    @GetMapping("api/public/logout")
    public ResponseEntity<Object> logout(@RequestHeader String token){
        String email = jwtService.decode(token);
        if(userRepo.findByEmail(email) != null
                || managerRepo.findByEmail(email) != null
                || adminRepo.findByEmail(email) != null){
            return ResponseEntity.ok(MyResponse.success(true));
        }
        return ResponseEntity.ok(MyResponse.success(false));
    }

    @GetMapping("api/public/get_role")
    public ResponseEntity<Object> getRole(@RequestHeader String token){
        String email = jwtService.decode(token);
        if(userRepo.findByEmail(email) != null){
            return ResponseEntity.ok(MyResponse.success("user"));
        }else if(managerRepo.findByEmail(email) != null){
            return ResponseEntity.ok(MyResponse.success("manager"));
        }else if(adminRepo.findByEmail(email) != null){
            return ResponseEntity.ok(MyResponse.success("admin"));
        }else {
            return ResponseEntity.ok(MyResponse.fail("invalidate token"));
        }
    }


}
