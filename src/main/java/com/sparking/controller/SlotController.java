package com.sparking.controller;

import com.sparking.entities.data.Slot;
import com.sparking.entities.jsonResp.MyResponse;
import com.sparking.security.JWTService;
import com.sparking.service.SlotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SlotController {

    private static Logger logger = LoggerFactory.getLogger(SlotController.class);

    @Autowired
    SlotService slotService;

    @Autowired
    JWTService jwtService;

    @PostMapping("api/ad/slot/create_and_update")
    public ResponseEntity<Object> createAndUpdate(@RequestBody Slot slot){
        return ResponseEntity.ok(MyResponse.success(slotService.createAndUpdate(slot)));
    }

    @PostMapping("api/mn/slot/create_and_update")
    public ResponseEntity<Object> updateMan(@RequestBody Slot slot ,@RequestHeader String token){
        logger.info(slot.toString());
        String email = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(slotService.managerCreateAndUpdate(email, slot)));
    }

    @GetMapping(value = {"api/ad/slot/find_all"})
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(slotService.findAll()));
    }

    @GetMapping(value = {"api/mn/slot/find_all"})
    public ResponseEntity<Object> mnFindAll(@RequestHeader String token){
        return ResponseEntity.ok(MyResponse.success(slotService.mnFindAll(token)));
    }

    @DeleteMapping("api/ad/slot/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(MyResponse.success(slotService.delete(id)));
    }

    @DeleteMapping("api/mn/slot/delete/{id}")
    public ResponseEntity<Object> deleteMan(@PathVariable int id, @RequestHeader String token){
        String email = jwtService.decode(token);
        return ResponseEntity.ok(MyResponse.success(slotService.managerDelete(email, id)));
    }

    @GetMapping(value = {"api/public/slot/find_by_id/{id}"})
    public ResponseEntity<Object> findById(@PathVariable Integer id){
        return ResponseEntity.ok(MyResponse.success(slotService.findById(id)));
    }

}
