package com.sparking.service_impl;

import com.sparking.entities.data.Tag;
import com.sparking.entities.payloadReq.RegisterTagsPayload;
import com.sparking.repository.TagRepo;
import com.sparking.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService_Impl implements TagService {
    @Autowired
    TagRepo tagRepo;

    @Override
    public Tag registerTagForUser(RegisterTagsPayload registerTagsPayload) {
        return tagRepo.registerTagForUser(registerTagsPayload);
    }
}
