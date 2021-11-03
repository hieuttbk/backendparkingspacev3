package com.sparking.controller;

import com.sparking.entities.data.Slot;
import com.sparking.entities.jsonResp.MyResponse;
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

    @PostMapping("api/ad/slot/create_and_update")
    public ResponseEntity<Object> createAndUpdate(@RequestBody Slot slot){
        return ResponseEntity.ok(MyResponse.success(slotService.createAndUpdate(slot)));
    }

    @PostMapping("api/mn/slot/create")
    public ResponseEntity<Object> createMan(@RequestBody Slot slot){
        logger.info(slot.toString());
        return ResponseEntity.ok(MyResponse.success(slotService.createAndUpdate(slot)));
    }

    @PostMapping("api/mn/slot/update")
    public ResponseEntity<Object> updateMan(@RequestBody Slot slot){
        logger.info(slot.toString());
        return ResponseEntity.ok(MyResponse.success(slotService.createAndUpdate(slot)));
    }

    @GetMapping(value = {"api/public/slot/find_all","api/ad/slot/find_all"})
    public ResponseEntity<Object> findAll(){
        return ResponseEntity.ok(MyResponse.success(slotService.findAll()));
    }

    @DeleteMapping("api/ad/slot/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id){
        return ResponseEntity.ok(MyResponse.success(slotService.delete(id)));
    }

    @DeleteMapping("api/mn/slot/delete/{id}")
    public ResponseEntity<Object> deleteMan(@PathVariable int id){
        return ResponseEntity.ok(MyResponse.success(slotService.delete(id)));
    }

    @GetMapping(value = {"api/public/slot/find_by_id/{id}"})
    public ResponseEntity<Object> findById(@PathVariable Integer id){
        return ResponseEntity.ok(MyResponse.success(slotService.findById(id)));
    }

}
