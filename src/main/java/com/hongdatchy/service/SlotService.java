package com.hongdatchy.service;

import com.hongdatchy.entities.data.Slot;
import com.hongdatchy.entities.json.SlotJson;

import java.util.List;

public interface SlotService {

    SlotJson createAndUpdate(Slot slot);

    boolean delete(int id);

    List<SlotJson> findAll();

    SlotJson findById(int id);
}
