package com.sparking.controller;

import com.sparking.entities.data.TagPackage;
import com.sparking.entities.jsonResp.MyResponse;
import com.sparking.entities.payloadReq.GetNewsTagPayload;
import com.sparking.entities.payloadReq.RegisterTagsPayload;
import com.sparking.helper.GetPathRequestHandler;
//import com.sparking.repository.ManagerRepo;
import com.sparking.security.JWTService;
import com.sparking.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class TagController {
    @Autowired
    TagService tagService;

//    @Autowired
//    ManagerRepo managerRepo;

    @Autowired
    JWTService jwtService;

    @PostMapping("api/ad/tags/register")
    public ResponseEntity<Object> registerTagForUser(@RequestBody RegisterTagsPayload registerTagsPayload) {
        return ResponseEntity.ok(
                MyResponse.success(tagService.registerTagForUser(registerTagsPayload))
        );
    }

    // Params {id} to getAll or getOne
    @GetMapping("api/ad/tags/{id}")
    public ResponseEntity<Object> getNewsTag(@PathVariable Integer id) {
        return ResponseEntity.ok(
                MyResponse.success(tagService.getNewsTag(id))
        );
    }

    @GetMapping("api/ad/tags")
    public ResponseEntity<Object> getAllNewsTag() {
        return ResponseEntity.ok(
                MyResponse.success(tagService.getAllNewsTag())
        );
    }

    @DeleteMapping(value = { "api/ad/tags/{id}", "api/ad/tag/{id}" })
    public ResponseEntity<Object> deleteTag(@PathVariable int id) throws Exception {
        String pathRequest = GetPathRequestHandler.getPathRequest();

//        System.out.print(pathRequest);
        return ResponseEntity.ok(
                MyResponse.success(tagService.deleteTag(id, pathRequest))
        );
    }

    @PostMapping(value = {"api/mn/tags/{id}", "api/ad/tags/{id}"})
    public ResponseEntity<Object> updateTag(@RequestBody TagPackage tagPackage, @PathVariable Integer id) {
        if (id == null) {
            return null;
        }
        return ResponseEntity.ok(
                MyResponse.success(tagService.updateTag(tagPackage, id))
        );
    }

    @PostMapping("api/ad/tags/filter")
    public ResponseEntity<Object> filterNewsTag(@RequestBody GetNewsTagPayload getNewsTagPayload) {
        return ResponseEntity.ok(
          MyResponse.success(tagService.filterNewsTag(getNewsTagPayload))
        );
    }
}
