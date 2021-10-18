package com.sparking.service_impl;

import com.sparking.common.Utils;
import com.sparking.entities.data.Contract;
import com.sparking.entities.data.Field;
import com.sparking.entities.payloadReq.ContractPayload;
import com.sparking.repository.ContractRepo;
import com.sparking.repository.FieldRepo;
import com.sparking.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractService_Impl implements ContractService {



    @Value("${timeConditionDelay}")
    String timeConditionDelay;

    @Autowired
    ContractRepo contractRepo;

    @Autowired
    FieldRepo fieldRepo;

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

    public List<Contract> findByTime(String type, String t1, String t2) throws ParseException {

        List<Contract> contracts = findAll();
        Timestamp timestamp1 = Utils.getTime(t1);
        Timestamp timestamp2 = Utils.getTime(t2);
        return contracts.stream().filter(contract -> {
            Timestamp timestamp = null;
            switch (type){
                case "timeInBook":
                    timestamp = contract.getTimeInBook();
                    break;
                case "timeOutBook":
                    timestamp = contract.getTimeOutBook();
                    break;
                case "dtCreate":
                    timestamp = contract.getDtCreate();
                    break;
                default:
                    break;
            }
            if(timestamp == null){
                return false;
            }
//            System.out.println(timestamp1);
//            System.out.println(timestamp);
//            System.out.println(timestamp2);
//            System.out.println(timestamp.after(timestamp1));
//            System.out.println(timestamp.before(timestamp2));
//            System.out.println("----------------");
            return timestamp.after(timestamp1) && timestamp.before(timestamp2);
        }).collect(Collectors.toList());
    }

}
