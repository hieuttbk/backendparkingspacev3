package com.sparking.service_impl;

import com.sparking.entities.data.*;
import com.sparking.entities.jsonResp.FieldAnalysis;
import com.sparking.entities.jsonResp.FieldJson;
import com.sparking.repository.*;
import com.sparking.security.JWTService;
import com.sparking.service.ContractService;
import com.sparking.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;

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

    @Autowired
    StatsFieldRepo statsFieldRepo;

    @Autowired
    JWTService jwtService;

    @Override
    public FieldJson createAndUpdate(Field field) {
        Field oldField = fieldRepo.findById(field.getId());

        FieldJson fieldJson = data2Json(field);
        fieldRepo.createAndUpdate(field);
        if(oldField == null){ // chi tao slot theo so space khi dang them moi field
        if (oldField != null) { // chi tao slot theo so space khi dang them moi field
//            System.out.println("OldField");
//            System.out.println(fieldJson.getSpace().intValue());
            for (int i = 0; i < fieldJson.getSpace().intValue(); i++) {
                System.out.println("Field Service - " + i);
                slotRepo.createAndUpdate(
                        new Slot(i + 1, field.getId(), false, false)
                );
            }
        }
//        System.out.println("Return FieldJson");
        return fieldJson;
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
        if (manager == null) {
            return null;
        }
        return fieldRepo.managerFind(manager).stream().map(this::data2Json).collect(Collectors.toList());
    }

    @Override
    public FieldJson managerUpdate(Field field, String phone) {
        Manager manager = managerRepo.findByEmail(phone);
        if (manager == null) {
            return null;
        }
        return data2Json(fieldRepo.managerUpdate(field, manager));
    }

    @Override
    public boolean managerDelete(int id, String phone) {
        Manager manager = managerRepo.findByEmail(phone);
        if (manager == null) {
            return false;
        }
        return fieldRepo.managerDelete(id, manager);
    }

    @Override
    public List<FieldAnalysis> analysis(int fieldId, long since, long until, String unit) throws ParseException {
        //System.out.println("analysis " + since + " " + fieldId );
        Field field = fieldRepo.findById(fieldId);
        int totalSlot = (int) slotRepo.findAll().stream().filter(slot -> slot.getFieldId() == fieldId).count();
        if (field == null) {
            return null;
        }
        int n, unitInt;

        switch (unit) {
            case "hour":
                unitInt = 60;
                break;
            case "day":
                unitInt = 60 * 24;
                break;
            case "week":
                unitInt = 60 * 24 * 7;
                break;
            case "month":
                unitInt = 60 * 24 * 12;
                break;
            default:
                return null;
        }

        unitInt *= 60 * 1000;
        final int oneHour = 60 * 60 * 1000;

        //  int time = since*60*1000;
        long time = since; // no convert to minute
        n = (int) ((until - since) / unitInt);
        List<FieldAnalysis> rs = new ArrayList<>();

        //TODO
        //optimize analysis
        for (int i = 0; i < n; i++) {
            long tt = time;
            int f = 0;
            int c = 0;

            while (tt < time + unitInt) {
                List<StatsField> s = statsFieldRepo.findByTime(since, until);
                f = (int) s.stream().count();

                tt += oneHour * 24; // 1h to millisecond
                c++;
            }
            f /= c;
            rs.add(FieldAnalysis.builder()
                    .totalSlot(totalSlot)
                    .time(time)
                    .freq(f)
                    .cost((int) (f * field.getPrice() * unitInt / oneHour))
                    .build());

            time += unitInt;
        }


        if (unit.equals("hour")) {
            for (int i = 0; i < n; i++) {
                long t = time;
                int freq = 0;
                int count = 0;


                while (t < time + unitInt) {
                    //   Timestamp t_1= new Timestamp(System.currentTimeMillis());
//                List<Contract> contractsByTime= contractService.findByTime(new Timestamp(t), new Timestamp(t + oneHour));
//                Timestamp t_2= new Timestamp(System.currentTimeMillis());
//                int c = (int) contractsByTime.stream().filter(contract -> contract.getFieldId() == fieldId).count();

                    // old code, for hour in day
                    int c1 = (int) contractService.findByFieldTime(new Timestamp(t), new Timestamp(t + oneHour), fieldId).stream().count();
                    //try querry fieldid before
                    //  List<Contract> contractsByField= contractService.findByField(fieldId);

                    // Timestamp t_3= new Timestamp(System.currentTimeMillis());
                    // System.out.println("t2-t1: " + (t_2.getTime()-t_1.getTime()) + " | t3-t2: " + (t_3.getTime()-t_2.getTime()));

                    freq += c1;
//                freq += (int) contractService.findByTime(new Timestamp(t), new Timestamp(t + oneHour))
//                        .stream().filter(contract -> contract.getFieldId() == fieldId).count();
                    //  List<Contract> contracts = contractService.findByTime(new Timestamp(t), new Timestamp(t + oneHour));

                    t += oneHour; // 1h to millisecond
                    count++;

//                for (Contract c:contracts) {
//                    System.out.println("Contract " + count + " " + freq + " " + c.getId());
//                }

                }

                freq /= count;

                rs.add(FieldAnalysis.builder()
                        .totalSlot(totalSlot)
                        .time(time)
                        .freq(freq)
                        .cost((int) (freq * field.getPrice() * unitInt / oneHour))
                        .build());
                time += unitInt;
            }
        }


        return rs;
    }

    @Override
    public List<FieldAnalysis> mnAnalysis(int fieldId, long since, long until, String unit, String token) throws ParseException {
        String email = jwtService.decode(token);
        Manager manager = managerRepo.findByEmail(email);
        List<Field> fieldsOfThisMn = fieldRepo.managerFind(manager);
        for (Field field : fieldsOfThisMn) {
            if (field.getId().equals(fieldId)) {
                return analysis(fieldId, since, until, unit);
            }
        }
        return null;
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
