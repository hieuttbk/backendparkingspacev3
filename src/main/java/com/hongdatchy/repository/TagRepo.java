package com.hongdatchy.repository;

import com.hongdatchy.entities.data.Tag;

import java.util.List;

public interface TagRepo {

    Tag createAndUpdate(Tag tag);

    boolean delete(int id);

    List<Tag> findAll();

}
