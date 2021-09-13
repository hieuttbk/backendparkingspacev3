package com.sparking.repository;

import com.sparking.entities.data.Slot;

import java.util.List;

public interface SlotRepo {

    Slot createAndUpdate(Slot slot);

    boolean delete(int id);

    List<Slot> findAll();

    Slot findById(int id);

}
