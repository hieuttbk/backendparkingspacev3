package com.sparking.repository;

import com.sparking.entities.data.District;
import com.sparking.entities.data.Manager;

import java.util.List;

public interface DistrictRepo {
    List<District> getAllDistrict();

    District getDistrictByID(int district);

    List<District> mnGetAllDistricts(Manager manager);
}
