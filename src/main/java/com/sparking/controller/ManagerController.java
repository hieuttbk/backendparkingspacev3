package com.sparking.controller;

import com.sparking.entities.jsonResp.MyResponse;
import com.sparking.entities.payloadReq.ChangePassForm;
import com.sparking.entities.payloadReq.ManagerPayload;
import com.sparking.entities.payloadReq.VerifyResetPassPayload;
import com.sparking.security.JWTService;
import com.sparking.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ManagerController {
    @Autowired
    ManagerService managerService;

    @Autowired
    JWTService jwtService;


    @PostMapping("api/mn/verify_reset_pass")
    public ResponseEntity<Object> resetPass(@RequestBody VerifyResetPassPayload verifyResetPassPayload) {
        return ResponseEntity.ok(MyResponse.success(managerService.verifyResetPass(verifyResetPassPayload)));
    }

    @PostMapping("api/mn/reset_pass")
    public ResponseEntity<Object> resetPass(@RequestBody String email) {
        return ResponseEntity.ok(MyResponse.success(managerService.resetPass(email)));
    }

    @PostMapping("api/mn/changePass")
    public ResponseEntity<Object> changePass(@RequestBody ChangePassForm changePassForm, @RequestHeader String token){
        String email = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(managerService.changePass(changePassForm, email)));
    }

    @PostMapping("api/ad/manager/create_and_update")
    public ResponseEntity<Object> createAndUpdate(@RequestBody ManagerPayload managerPayload){
        return ResponseEntity.ok(MyResponse.success(managerService.createAndUpdate(managerPayload)));
    }

    @GetMapping("api/ad/manager/find_all")
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(managerService.findAll()));
    }

    @DeleteMapping("api/ad/manager/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(MyResponse.success(managerService.delete(id)));
    }

}
