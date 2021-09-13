package com.sparking.service;

import com.sparking.entities.data.Slot;
import com.sparking.entities.jsonResp.SlotJson;

import java.util.List;

public interface SlotService {

    SlotJson createAndUpdate(Slot slot);

    boolean delete(int id);

    List<SlotJson> findAll();

    SlotJson findById(int id);
}
