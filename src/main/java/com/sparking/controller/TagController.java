package com.sparking.controller;

import com.sparking.entities.jsonResp.MyResponse;
import com.sparking.entities.payloadReq.RegisterTagsPayload;
import com.sparking.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {
    @Autowired
    TagService tagsService;

    @PostMapping("api/ad/tags")
    public ResponseEntity<Object> registerTagForUser(@RequestBody RegisterTagsPayload registerTagsPayload) {
        return ResponseEntity.ok(
                MyResponse.success(tagsService.registerTagForUser(registerTagsPayload))
        );
    }
}
