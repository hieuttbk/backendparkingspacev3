package com.hongdatchy.controller;

import com.hongdatchy.entities.data.Slot;
import com.hongdatchy.entities.json.MyResponse;
import com.hongdatchy.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SlotController {

    @Autowired
    SlotService slotService;

    @PostMapping("api/ad/slot/create_and_update")
    public ResponseEntity<Object> createAndUpdate(@RequestBody Slot slot){
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

    @GetMapping(value = {"api/public/slot/find_by_id/{id}"})
    public ResponseEntity<Object> findById(@PathVariable Integer id){
        return ResponseEntity.ok(MyResponse.success(slotService.findById(id)));
    }

}
