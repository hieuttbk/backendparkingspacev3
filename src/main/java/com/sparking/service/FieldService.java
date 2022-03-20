package com.sparking.service;

import com.sparking.entities.data.Field;
import com.sparking.entities.jsonResp.FieldAnalysis;
import com.sparking.entities.jsonResp.FieldJson;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface FieldService {

    FieldJson createAndUpdate(Field field);

    boolean delete(int id);

    List<FieldJson> filterByDistrictAndArea(String district, String area);

    List<FieldJson> filterByDistrict(String district);

    List<FieldJson> filterByArea(String area);

    List<FieldJson> findAll();

    List<FieldJson> managerFind(String phone);

    FieldJson managerUpdate(Field field, String phone);

    boolean managerDelete(int id, String phone);

    List<FieldAnalysis>analysis(int fieldId, long since, long until, String unit) throws ParseException;

    List<FieldAnalysis>mnAnalysis(int fieldId, long since, long until, String unit, String token) throws ParseException;

    FieldJson data2Json(Field field);

    List<FieldAnalysis> analysisByHour(int fieldId, long since, long until, String unit);
}
