package com.sparking.service;

import com.sparking.entities.data.Contract;
import com.sparking.entities.payloadReq.ContractPayload;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;


public interface ContractService {

    Contract createAndUpdate(ContractPayload contractPayload);

    boolean delete(int id);

    List<Contract> findAll();

    Contract payload2data(ContractPayload contractPayload);

    double getCost(Timestamp timeCarin, Timestamp timeCarOut, Timestamp timeBookIn, Timestamp timeBookOut, double price);

    List<Contract> findByTime(String type, String t1, String t2) throws ParseException;
}
