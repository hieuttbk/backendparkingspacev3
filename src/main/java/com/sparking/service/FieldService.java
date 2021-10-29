package com.sparking.service;

import com.sparking.entities.data.Field;
import com.sparking.entities.jsonResp.FieldAnalysis;
import com.sparking.entities.jsonResp.FieldJson;

import java.text.ParseException;
import java.util.List;

public interface FieldService {

    FieldJson createAndUpdate(Field field);

    boolean delete(int id);

    List<FieldJson> findAll();

    List<FieldJson> managerFind(String phone);

    FieldJson managerUpdate(Field field, String phone);

    boolean managerDelete(int id, String phone);

    List<FieldAnalysis>analysis(int fieldId, int since, int until, String unit) throws ParseException;

    FieldJson data2Json(Field field);

}
