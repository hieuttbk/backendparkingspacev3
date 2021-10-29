package com.sparking.controller;

import com.sparking.entities.data.Field;
import com.sparking.entities.jsonResp.MyResponse;
import com.sparking.security.JWTService;
import com.sparking.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class FieldController {

    @Autowired
    FieldService fieldService;

    @Autowired
    JWTService jwtService;

    @PostMapping("api/ad/field/create_and_update")
    public ResponseEntity<Object> createAndUpdate(@RequestBody Field field){
        return ResponseEntity.ok(MyResponse.success(fieldService.createAndUpdate(field)));
    }

//    /api/ad/analysis?field=1&since=3454764&util=45647564&unit=hour
    @PostMapping("api/ad/analysis")
    public ResponseEntity<Object> analysis(@RequestParam int field, @RequestParam int since,
                                           @RequestParam int until, String unit) throws ParseException {
        return ResponseEntity.ok(MyResponse.success(fieldService.analysis(field, since, until, unit)));
    }

    @GetMapping(value = {"api/public/field/find_all","api/ad/field/find_all"})// can multiple mapping
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(fieldService.findAll()));
    }

    @DeleteMapping("api/ad/field/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(MyResponse.success(fieldService.delete(id)));
    }

    @GetMapping(value = {"api/mn/field/find_all"})
    public ResponseEntity<Object> managerFindAll(@RequestHeader String token){
        String phone = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(fieldService.managerFind(phone)));
    }

    @PostMapping(value = {"api/mn/field/update"})
    public ResponseEntity<Object> managerUpdate(@RequestBody Field field ,@RequestHeader String token){
        String phone = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(fieldService.managerUpdate(field,phone)));
    }

    @DeleteMapping("api/mn/field/delete/{id}")
    public ResponseEntity<Object> managerDelete(@PathVariable int id, @RequestHeader String token){
        String phone = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(fieldService.managerDelete(id, phone)));
    }

}
