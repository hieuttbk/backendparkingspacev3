package com.sparking.service;

import com.sparking.entities.data.Area;
import com.sparking.entities.jsonResp.MyResponse;

import java.util.List;

public interface AreaService {
    List<Area> getAllAreas();

    MyResponse mnGetAllAreas(String decode);
}
