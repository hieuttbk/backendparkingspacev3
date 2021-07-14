package com.hongdatchy.repository;

import com.hongdatchy.entities.data.Detector;
import com.hongdatchy.entities.data.Gateway;
import com.hongdatchy.entities.data.Manager;

import java.util.List;

public interface DetectorRepo {

    Detector createAndUpdate(Detector detector);

    boolean delete(int id);

    List<Detector> findAll();

    List<Detector> findBySlotId(int id);

    List<Detector> managerFind(Manager manager);

    Detector managerCreateAndUpdate(Detector detector, Manager manager);

    boolean managerDelete(int id, Manager manager);

    Detector findById(int id);

    Detector managerFindById(int id, Manager manager);
}
