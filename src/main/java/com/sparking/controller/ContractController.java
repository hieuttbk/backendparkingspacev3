package com.sparking.controller;

import com.sparking.entities.jsonResp.MyResponse;
import com.sparking.entities.payloadReq.ContractPayload;
import com.sparking.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContractController {

    @Autowired
    ContractService contractService;

    @PostMapping("api/ad/contract/create_and_update")
    public ResponseEntity<Object> createAndUpdate(@RequestBody ContractPayload contractPayload){
        return ResponseEntity.ok(MyResponse.success(contractService.createAndUpdate(contractPayload)));
    }

    @GetMapping("api/ad/contract/find_all")
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(contractService.findAll()));
    }

    @GetMapping("api/mn/contract/find_all")
    public ResponseEntity<Object> findAllMan(){
        return ResponseEntity.ok(MyResponse.success(contractService.findAll()));
    }

    @DeleteMapping("api/ad/contract/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(MyResponse.success(contractService.delete(id)));
    }

}
