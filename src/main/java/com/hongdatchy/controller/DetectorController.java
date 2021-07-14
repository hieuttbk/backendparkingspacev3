package com.hongdatchy.controller;

import com.hongdatchy.entities.data.Detector;
import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.entities.payload.DetectorPayload;
import com.hongdatchy.security.JWTService;
import com.hongdatchy.service.DetectorService;
import com.hongdatchy.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DetectorController {

    @Autowired
    DetectorService detectorService;

    @Autowired
    JWTService jwtService;

//    @PostMapping("api/ad/detector/create_and_update")
//    public ResponseEntity<Object> createAndUpdate(@RequestBody DetectorPayload detectorPayload){
//        return ResponseEntity.ok(MyResponse.success(detectorService.createAndUpdate(detectorPayload)));
//    }

    @GetMapping("api/ad/detector/find_all")
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(detectorService.findAll()));
    }

//    @DeleteMapping("api/ad/detector/delete/{id}")
//    public ResponseEntity<Object> delete(@PathVariable int id){
//        return ResponseEntity.ok(MyResponse.success(detectorService.delete(id)));
//    }

    @GetMapping(value = {"api/mn/detector/find_all"})
    public ResponseEntity<Object> managerFindAll(@RequestHeader String token){
        String phone = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(detectorService.managerFind(phone)));
    }

//    @PostMapping("api/mn/detector/create_and_update")
//    public ResponseEntity<Object> managerCreateAndUpdate(@RequestBody DetectorPayload detectorPayload
//            , @RequestHeader String token){
//        String phone = jwtService.decode(token);
//        return ResponseEntity.ok(MyResponse.success(detectorService.managerCreateAndUpdate(detectorPayload, phone)));
//    }
//
//    @DeleteMapping("api/mn/detector/delete/{id}")
//    public ResponseEntity<Object> managerDelete(@PathVariable int id, @RequestHeader String token){
//        String phone = jwtService.decode(token);
//        return ResponseEntity.ok(MyResponse.success(detectorService.managerDelete(id, phone)));
//    }

    @GetMapping("api/ad/detector/find_by_id/{id}")
    public ResponseEntity<Object> findById(@PathVariable Integer id){
        return ResponseEntity.ok(MyResponse.success(detectorService.findById(id)));
    }

    @GetMapping(value = {"api/mn/detector/find_by_id/{id}"})
    public ResponseEntity<Object> managerFindById(@PathVariable Integer id,@RequestHeader String token){
        String phone = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(detectorService.managerFindById(id, phone)));
    }
}
