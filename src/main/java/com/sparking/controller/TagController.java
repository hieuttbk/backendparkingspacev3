package com.sparking.controller;

import com.sparking.entities.data.Tag;
import com.sparking.entities.jsonResp.MyResponse;
import com.sparking.entities.payloadReq.GetNewsTagPayload;
import com.sparking.entities.payloadReq.RegisterTagsPayload;
import com.sparking.service.TagService;
import org.apache.http.client.fluent.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @PutMapping("api/ad/tags")
    public ResponseEntity<Object> updateTagForUser(@RequestBody Tag tag) {
        return ResponseEntity.ok(
                MyResponse.success(tagService.updateTagForUser(tag))
        );
    }

    @DeleteMapping("api/ad/tags")
    public ResponseEntity<Object> deleteTagForUser(@RequestParam String id) {
        return ResponseEntity.ok(
                MyResponse.success(tagService.deleteTagForUser(id))
        );
    }

    @GetMapping("api/ad/tags")
    public ResponseEntity<Object> getAllTags() {
        return ResponseEntity.ok(
                MyResponse.success(tagService.getAllTags())
        );
    }


    @GetMapping("api/ad/tag_packages")
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
