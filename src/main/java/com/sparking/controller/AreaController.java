package com.sparking.controller;

import com.sparking.entities.jsonResp.MyResponse;
import com.sparking.security.JWTService;
import com.sparking.service.AreaService;
import org.apache.http.client.fluent.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AreaController {
    @Autowired
    private AreaService areaService;

    @Autowired
    private JWTService jwtService;

    @GetMapping("api/ad/areas")
    public ResponseEntity<Object> getAllAreas() {
        return ResponseEntity.ok(MyResponse.success(areaService.getAllAreas()));
    }

    @GetMapping("api/mn/areas")
    public ResponseEntity<Object> managerGetAllAreas(@RequestHeader String token) {
        String decode = jwtService.decode(token);
        return ResponseEntity.ok(areaService.mnGetAllAreas(decode));
    }
}
