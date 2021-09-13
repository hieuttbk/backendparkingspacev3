package com.sparking.service_impl;

import com.sparking.entities.data.Field;
import com.sparking.entities.data.Manager;
import com.sparking.entities.json.FieldJson;
import com.sparking.repository.ContractRepo;
import com.sparking.repository.FieldRepo;
import com.sparking.repository.ManagerRepo;
import com.sparking.repository.SlotRepo;
import com.sparking.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FieldService_Impl implements FieldService {

    @Autowired
    FieldRepo fieldRepo;

    @Autowired
    SlotRepo slotRepo;

    @Autowired
    ManagerRepo managerRepo;

    @Autowired
    ContractRepo contractRepo;

    @Override
    public FieldJson createAndUpdate(Field field) {
        return data2Json(fieldRepo.createAndUpdate(field));
    }

    @Override
    public boolean delete(int id) {
        return fieldRepo.delete(id);
    }

    @Override
    public List<FieldJson> findAll() {
        return fieldRepo.findAll().stream().map(this::data2Json).collect(Collectors.toList());
    }

    @Override
    public List<Field> managerFind(String phone) {
        Manager manager = managerRepo.findByEmail(phone);
        if(manager == null){
            return null;
        }
        return fieldRepo.managerFind(manager);
    }

    @Override
    public Field managerUpdate(Field field, String phone) {
        Manager manager = managerRepo.findByEmail(phone);
        if(manager == null){
            return null;
        }
        return fieldRepo.managerUpdate(field, manager);
    }

    @Override
    public boolean managerDelete(int id, String phone) {
        Manager manager = managerRepo.findByEmail(phone);
        if(manager == null){
            return false;
        }
        return fieldRepo.managerDelete(id, manager);
    }
    @Override
    public FieldJson data2Json(Field field) {
        return FieldJson.builder()
                .id(field.getId())
                .totalBook((int) contractRepo.findAll().stream()
                        .filter(contract -> (
                                (contract.getStatus().equals("V"))
                                && contract.getFieldId().equals(field.getId())
                        ))
                        .count())
                .busySlot((int) slotRepo.findAll().stream()
                        .filter(slot -> ((slot.getStatusDetector() != null && slot.getStatusDetector())
                                || (slot.getStatusCam() != null && slot.getStatusCam()))
                                && slot.getFieldId().equals(field.getId()))
                        .count())
                .latitude(field.getLatitude())
                .longitude(field.getLongitude())
                .address(field.getAddress())
                .name(field.getName())
                .totalSlot((int) slotRepo.findAll().stream()
                        .filter(slot -> slot.getFieldId().equals(field.getId())).count())
                .details(field.getDetails())
                .image(field.getImage())
                .openstatus(field.getOpenstatus())
                .price(field.getPrice())
                .space(field.getSpace())
                .build();
    }

}
