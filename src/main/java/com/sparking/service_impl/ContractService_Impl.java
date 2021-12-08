package com.sparking.service_impl;

import com.sparking.BackendParkingSpaceV2Application;
import com.sparking.common.Utils;
import com.sparking.entities.data.Contract;
import com.sparking.entities.data.Field;
import com.sparking.entities.jsonResp.FieldJson;
import com.sparking.entities.payloadReq.ContractPayload;
import com.sparking.repository.ContractRepo;
import com.sparking.repository.FieldRepo;
import com.sparking.repository.ManagerRepo;
import com.sparking.repository.SlotRepo;
import com.sparking.service.ContractService;
import com.sparking.service.FieldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractService_Impl implements ContractService {
    private static Logger logger = LoggerFactory.getLogger(ContractService_Impl.class);


    @Value("${timeConditionDelay}")
    String timeConditionDelay;

    @Autowired
    ManagerRepo managerRepo;

    @Autowired
    ContractRepo contractRepo;

    @Autowired
    FieldRepo fieldRepo;

    @Autowired
    FieldService fieldService;

    @Autowired
    SlotRepo slotRepo;

    @Override
    public Contract createAndUpdate(ContractPayload contractPayload) {
        return contractRepo.createAndUpdate(payload2data(contractPayload));
    }

    @Override
    public boolean delete(int id) {
        return contractRepo.delete(id);
    }

    @Override
    public List<Contract> findAll() {
        return contractRepo.findAll();
    }

    @Override
    public List<Contract> findByQuantity(String quantity) {
        return contractRepo.findByQuantity(quantity);
    }

    @Override
    public Contract payload2data(ContractPayload contractPayload){
        double cost;
        if(contractPayload.getTimeCarIn() == null || contractPayload.getTimeCarOut() == null){
            cost = 0;
        }else{
            List<Field> fields = fieldRepo.findAll().stream()
                    .filter(field -> (field.getId().equals(contractPayload.getFieldId())))
                    .collect(Collectors.toList());
            if(fields.size() == 0){
                return null;
            }
            double price= fields.get(0).getPrice();
            cost = getCost(contractPayload.getTimeCarIn(), contractPayload.getTimeCarOut()
                    , contractPayload.getTimeInBook(), contractPayload.getTimeCarOut(), price);
        }
        return Contract.builder()
                .id(contractPayload.getId())
                .fieldId(contractPayload.getFieldId())
                .userId(contractPayload.getUserId())
                .timeCarIn(null)
                .timeCarOut(null)
                .timeOutBook(contractPayload.getTimeOutBook())
                .timeInBook(contractPayload.getTimeInBook())
                .carNumber(contractPayload.getCarNumber())
                .dtCreate(contractPayload.getDtCreate())
                .status(contractPayload.getStatus())
                .cost(cost == 0 ? "" : String.valueOf(cost))
                .build();
    }

    @Override
    public double getCost(Timestamp timeCarin, Timestamp timeCarOut, Timestamp timeBookIn, Timestamp timeBookOut, double price) {
        double cost;
        Timestamp timeCostIn, timeCostOut;
        if(timeCarin.getTime() < timeBookIn.getTime() + Integer.parseInt(timeConditionDelay)){
            timeCostIn = timeCarin;
        }else {
            timeCostIn = timeBookIn;
        }
        if(timeCarOut.getTime() < timeBookOut.getTime() - Integer.parseInt(timeConditionDelay)){
            timeCostOut = timeBookOut;
        }else {
            timeCostOut = timeCarOut;
        }
        cost = (double)(timeCostOut.getTime() - timeCostIn.getTime())/1000/60/60 * price;
        return cost;
    }

    public List<Contract> findByTime(String tt1, String tt2) throws ParseException {
        Timestamp t1 = Utils.getTime(tt1);
        Timestamp t2 = Utils.getTime(tt2);
        return findByTime(t1, t2);
    }

    @Override
    public List<Contract> findByTime(Timestamp t1, Timestamp t2) {
        List<Contract> contracts = findAll();
        return contracts.stream().filter(contract -> {
            Timestamp ti = contract.getTimeCarIn();
            Timestamp to = contract.getTimeCarOut();
            long dt = t2.getTime() - t1.getTime();
            double b = 0;

            if((!contract.getStatus().equals("R"))||(null==ti)||(null==to)) { // ti, to may be null in contracts
                return false;
            }
            if(t1.after(to)||t2.before(ti)){ // ti-to is out of t1-t2
                return false;
            }
//            logger.info("t1 " + t1);
//            logger.info("t2 " + t2);
//            logger.info("ti " + ti);
//            logger.info("to " + to);
            if(t1.before(ti)){
                b = (to.getTime()-ti.getTime())  * 1.0 / dt; //  * 1.0 de no tinh theo double
            }
            if(ti.before(t1) && t1.before(to)){
                b=(to.getTime()-t1.getTime()) * 1.0 / dt;
            }
            return b >0.6;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Contract> managerFind(String email) {
        List<FieldJson> fieldsOfThisManager = fieldService.managerFind(email);
        if(fieldsOfThisManager == null){
            return null;
        }
        List<Contract> rs = new ArrayList<>();

        for (FieldJson field: fieldsOfThisManager) {
            List<Contract> contracts = contractRepo.findAll().stream()
                    .filter(contract -> contract.getFieldId().equals(field.getId()))
                    .collect(Collectors.toList());
            rs.addAll(contracts);
        }
        return rs;
    }
}
