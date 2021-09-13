package com.sparking.service;

import com.sparking.entities.data.Contract;
import com.sparking.entities.payload.ContractPayload;

import java.sql.Timestamp;
import java.util.List;


public interface ContractService {

    Contract createAndUpdate(ContractPayload contractPayload);

    boolean delete(int id);

    List<Contract> findAll();

    Contract payload2data(ContractPayload contractPayload);

    double getCost(Timestamp timeCarin, Timestamp timeCarOut, Timestamp timeBookIn, Timestamp timeBookOut, double price);
}
