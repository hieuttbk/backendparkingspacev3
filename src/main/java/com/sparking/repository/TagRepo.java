package com.sparking.repository;

import com.sparking.entities.data.Tag;
import com.sparking.entities.data.TagPackage;
import com.sparking.entities.payloadReq.GetNewsTagPayload;
import com.sparking.entities.payloadReq.RegisterTagsPayload;

import java.util.List;

public interface TagRepo {

    Tag createAndUpdate(Tag tag);

    boolean delete(int id, String pathRequest);

    List<Tag> findAll();

    Tag findByTagId(String id);

    Tag registerTagForUser(RegisterTagsPayload registerTagsPayload);

    TagPackage getNewsTag(Integer id);

    List<TagPackage> getAllNewsTag();

    List<TagPackage> filterNewsTag(GetNewsTagPayload getNewsTagPayload);

    void createNewsFromTag(TagPackage tagPackage);

    TagPackage updateTag(TagPackage tagPackage, Integer id);
}
