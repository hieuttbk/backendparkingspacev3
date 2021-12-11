package com.sparking.service;

import com.sparking.entities.data.Slot;
import com.sparking.entities.jsonResp.SlotJson;

import java.util.List;

public interface SlotService {

    SlotJson createAndUpdate(Slot slot);

    boolean delete(int id);

    List<SlotJson> getByQuantity(String field, String quantity);

    List<SlotJson> getAll(String field);

    List<SlotJson> findAll();

    List<SlotJson> mnGetByQuantity(String token, String field, String quantity);

    List<SlotJson> mnGetAll(String email, String field);

    SlotJson findById(int id);

    SlotJson managerCreateAndUpdate(String email, Slot slot);

    boolean managerDelete(String email, int id);
}
