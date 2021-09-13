package com.sparking.service;

import com.sparking.entities.data.Detector;
import com.sparking.entities.payloadReq.DetectorPayload;

import java.util.List;

public interface DetectorService {

    Detector createAndUpdate(DetectorPayload detectorPayload);

    boolean delete(int id);

    List<Detector> findAll();

    List<Detector> managerFind(String phone);

    Detector managerCreateAndUpdate(DetectorPayload detectorPayload, String phone);

    boolean managerDelete(int id, String phone);

    Detector findById(int id);

    Detector managerFindById(int id, String phone);
}
