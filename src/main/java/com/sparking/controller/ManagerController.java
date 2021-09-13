package com.sparking.controller;

import com.sparking.entities.json.MyResponse;
import com.sparking.entities.payload.ManagerPayload;
import com.sparking.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ManagerController {
    @Autowired
    ManagerService managerService;

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
