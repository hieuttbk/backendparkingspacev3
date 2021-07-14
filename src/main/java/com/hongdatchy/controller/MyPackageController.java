package com.hongdatchy.controller;

import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.repository.PackageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPackageController {

    @Autowired
    PackageRepo packageRepo;

    @GetMapping(value = {"api/ad/package/find_all"})
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(packageRepo.findAll()));
    }
}
