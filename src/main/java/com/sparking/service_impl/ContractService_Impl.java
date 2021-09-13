package com.sparking.service_impl;

import com.sparking.entities.data.Contract;
import com.sparking.entities.data.Field;
import com.sparking.entities.payload.ContractPayload;
import com.sparking.repository.ContractRepo;
import com.sparking.repository.FieldRepo;
import com.sparking.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
        return cost = (double)(timeCostOut.getTime() - timeCostIn.getTime())/1000/60/60 * price;
    }

}
