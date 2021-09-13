package com.sparking.service;

import com.sparking.entities.data.ManagerField;
import com.sparking.entities.payload.ManagerFieldPayload;

import java.util.List;

public interface ManagerFieldService {

    ManagerField createAndUpdate(ManagerFieldPayload managerFieldPayload);

    boolean delete(int id);

    List<ManagerField> findAll();

}
