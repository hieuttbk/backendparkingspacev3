package com.sparking.repository;

import com.sparking.entities.data.Detector;
import com.sparking.entities.data.Manager;
import com.sparking.entities.payloadReq.UpdateSlotIdPayload;

import java.util.List;

public interface DetectorRepo {

    Detector createAndUpdate(Detector detector);

    boolean delete(int id);

    List<Detector> findAll();

    List<Detector> findBySlotId(int id);

    List<Detector> managerFind(Manager manager);

    Detector managerCreateAndUpdate(Detector detector, Manager manager);

    Detector updateSlotId(UpdateSlotIdPayload updateSlotIdPayload);

    boolean managerDelete(int id, Manager manager);

    Detector findById(int id);

    Detector managerFindById(int id, Manager manager);
}
