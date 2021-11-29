package com.sparking.repository;

import com.sparking.entities.data.Manager;
import com.sparking.entities.data.Slot;
import com.sparking.entities.jsonResp.SlotJson;

import java.util.List;

public interface SlotRepo {

    Slot createAndUpdate(Slot slot);

    boolean delete(int id);

    List<Slot> getAll(String field);

    List<Slot> findAll();

    Slot findById(int id);

    Slot managerCreateAndUpdate(Manager manager, Slot slot);

    boolean managerDelete(Manager manager, int id);

}
