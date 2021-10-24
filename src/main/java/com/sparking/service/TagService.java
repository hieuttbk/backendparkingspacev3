package com.sparking.service;

import com.sparking.entities.data.Tag;
import com.sparking.entities.payloadReq.RegisterTagsPayload;

public interface TagService {
    Tag registerTagForUser(RegisterTagsPayload registerTagsPayload);
}
