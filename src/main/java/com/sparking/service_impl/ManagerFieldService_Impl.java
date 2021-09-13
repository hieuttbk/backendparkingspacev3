package com.sparking.service_impl;

import com.sparking.entities.data.ManagerField;
import com.sparking.entities.payloadReq.ManagerFieldPayload;
import com.sparking.repository.ManagerFieldRepo;
import com.sparking.repository.ManagerRepo;
import com.sparking.service.ManagerFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class ManagerFieldService_Impl implements ManagerFieldService {

    @Autowired
    ManagerFieldRepo managerFieldRepo;

    @Autowired
    ManagerRepo managerRepo;

    @Override
    public ManagerField createAndUpdate(ManagerFieldPayload managerFieldPayload) {

        return managerFieldRepo.createAndUpdate(payload2Data(managerFieldPayload));
    }

    @Override
    public boolean delete(int id) {
        return managerFieldRepo.delete(id);
    }

    @Override
    public List<ManagerField> findAll() {
        return managerFieldRepo.findAll();
    }

    public ManagerField payload2Data(ManagerFieldPayload managerFieldPayload){
        return ManagerField.builder()
                .id(managerFieldPayload.getId())
                .lastTimeSetup(new Timestamp(new Date().getTime()))
                .fieldId(managerFieldPayload.getFieldId())
                .managerId(managerFieldPayload.getManagerId())
                .build();
    }
}
