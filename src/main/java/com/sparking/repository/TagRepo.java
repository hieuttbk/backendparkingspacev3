package com.sparking.repository;

import com.sparking.entities.data.Tag;

import java.util.List;

public interface TagRepo {

    Tag createAndUpdate(Tag tag);

    boolean delete(int id);

    List<Tag> findAll();

    Tag findByTagId(String id);
}
