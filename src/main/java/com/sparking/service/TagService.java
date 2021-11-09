package com.sparking.service;

import com.sparking.entities.data.Tag;
import com.sparking.entities.data.TagPackage;
import com.sparking.entities.payloadReq.GetNewsTagPayload;
import com.sparking.entities.payloadReq.RegisterTagsPayload;

import java.util.List;

public interface TagService {
    Tag registerTagForUser(RegisterTagsPayload registerTagsPayload);

    TagPackage getNewsTag(Integer id);

    List<TagPackage> getAllNewsTag();

    List<TagPackage> filterNewsTag(GetNewsTagPayload getNewsTagPayload);

    boolean deleteTag(int id, String pathRequest);

    TagPackage updateTag(TagPackage tagPackage, Integer id);
}
