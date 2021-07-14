package com.hongdatchy.service;

import com.hongdatchy.entities.data.Field;
import com.hongdatchy.entities.data.Manager;
import com.hongdatchy.entities.json.FieldJson;

import java.util.List;

public interface FieldService {

    FieldJson createAndUpdate(Field field);

    boolean delete(int id);

    List<FieldJson> findAll();

    List<Field> managerFind(String phone);

    Field managerUpdate(Field field, String phone);

    boolean managerDelete(int id, String phone);

    FieldJson data2Json(Field field);
}
