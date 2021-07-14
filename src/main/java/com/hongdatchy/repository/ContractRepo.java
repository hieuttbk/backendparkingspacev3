package com.hongdatchy.repository;

import com.hongdatchy.entities.data.Contract;

import java.util.List;

public interface ContractRepo {

    Contract createAndUpdate(Contract contract);

    boolean delete(int id);

    List<Contract> findAll();

//    List<Contract>  findBySlotId(int slotId);

}
