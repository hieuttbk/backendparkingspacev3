package com.hongdatchy.service;

import com.hongdatchy.entities.data.Contract;
import com.hongdatchy.entities.data.Field;
import com.hongdatchy.entities.payload.ContractPayload;

import java.sql.Timestamp;
import java.util.List;


public interface ContractService {

    Contract createAndUpdate(ContractPayload contractPayload);

    boolean delete(int id);

    List<Contract> findAll();

    Contract payload2data(ContractPayload contractPayload);

    double getCost(Timestamp timeCarin, Timestamp timeCarOut, Timestamp timeBookIn, Timestamp timeBookOut, double price);
}
