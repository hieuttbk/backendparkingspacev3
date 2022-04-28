package com.sparking.repository;

import com.sparking.entities.data.Area;
import com.sparking.entities.data.Manager;

import java.util.List;

public interface AreaRepo {
    List<Area> getAllAreas();

    Area getAreaById(int area);

    List<Area> mnGetAllAreas(Manager manager);
}
