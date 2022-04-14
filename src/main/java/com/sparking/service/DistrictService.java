package com.sparking.service;

import com.sparking.entities.data.District;
import com.sparking.entities.jsonResp.MyResponse;

import java.util.List;

public interface DistrictService {
    List<District> getAllDistrict();

    MyResponse mnGetAllDistricts(String decode);
}
