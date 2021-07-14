package com.hongdatchy.service;

import com.hongdatchy.entities.data.ManagerField;
import com.hongdatchy.entities.payload.ManagerFieldPayload;

import java.util.List;

public interface ManagerFieldService {

    ManagerField createAndUpdate(ManagerFieldPayload managerFieldPayload);

    boolean delete(int id);

    List<ManagerField> findAll();

}
