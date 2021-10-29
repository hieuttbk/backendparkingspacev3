package com.sparking.service_impl;

import com.sparking.common.ConfigVar;
import com.sparking.entities.data.Field;
import com.sparking.entities.data.Manager;
import com.sparking.entities.jsonResp.FieldAnalysis;
import com.sparking.entities.jsonResp.FieldJson;
import com.sparking.repository.ContractRepo;
import com.sparking.repository.FieldRepo;
import com.sparking.repository.ManagerRepo;
import com.sparking.repository.SlotRepo;
import com.sparking.service.ContractService;
import com.sparking.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    ContractService contractService;

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
    public List<FieldJson> managerFind(String email) {
        Manager manager = managerRepo.findByEmail(email);
        if(manager == null){
            return null;
        }
        return fieldRepo.managerFind(manager).stream().map(this::data2Json).collect(Collectors.toList());
    }

    @Override
    public FieldJson managerUpdate(Field field, String phone) {
        Manager manager = managerRepo.findByEmail(phone);
        if(manager == null){
            return null;
        }
        return data2Json(fieldRepo.managerUpdate(field, manager));
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
    public List<FieldAnalysis> analysis(int fieldId, int since, int until, String unit) throws ParseException {
        Field field = fieldRepo.findById(fieldId);
        int totalSlot = (int) slotRepo.findAll().stream().filter(slot -> slot.getFieldId() == fieldId).count();
        if(field == null){
            return null;
        }
        int  n, unitInt;

        switch (unit){
            case "hour":
                unitInt = 60;
                break;
            case "day":
                unitInt = 60*24;
                break;
            case "week":
                unitInt = 60*24*7;
                break;
            case "mouth":
                unitInt = 60*24*12;
                break;
            default:
                return null;
        }

        int time = since*60*1000;
        n = (until - since) / unitInt;
        List<FieldAnalysis> rs = new ArrayList<>();


        for(int i =0; i< n; i++){
            int t = time;
            int freq = 0;
            int count = 0;
            while (t < time + unitInt * 60 *1000){
                freq += (int) contractService.findByTime(new Timestamp(t), new Timestamp(t + 60 *1000))
                        .stream().filter(contract -> contract.getFieldId() == fieldId).count();
                t+= 60 *1000;
                count ++;
            }
            freq /= count;

            rs.add(FieldAnalysis.builder()
                    .totalSlot(totalSlot)
                    .time(time)
                    .freq(freq)
                    .cost((int) (freq * field.getPrice()  * unitInt))
                    .build());
            time += unitInt * 60 *1000;
        }



        return rs;
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
