package com.sparking.controller;

import com.sparking.entities.jsonResp.MyResponse;
import com.sparking.entities.payloadReq.GetNewsTagPayload;
import com.sparking.entities.payloadReq.RegisterTagsPayload;
import com.sparking.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {
    @Autowired
    TagService tagService;

    @PostMapping("api/ad/tags")
    public ResponseEntity<Object> registerTagForUser(@RequestBody RegisterTagsPayload registerTagsPayload) {
        return ResponseEntity.ok(
                MyResponse.success(tagService.registerTagForUser(registerTagsPayload))
        );
    }

    @GetMapping("api/ad/all-news-tag")
    public ResponseEntity<Object> getAllNewsTag() {
        return ResponseEntity.ok(
                MyResponse.success(tagService.getAllNewsTag())
        );
    }

    @PostMapping("api/ad/filter-news-tag")
    public ResponseEntity<Object> filterNewsTag(@RequestBody GetNewsTagPayload getNewsTagPayload) {
        return ResponseEntity.ok(
          MyResponse.success(tagService.filterNewsTag(getNewsTagPayload))
        );
    }
}
