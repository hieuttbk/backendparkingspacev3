package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.Contract;

import com.hongdatchy.entities.data.User;
import com.hongdatchy.repository.ContractRepo;
import com.hongdatchy.repository.SlotRepo;
import com.hongdatchy.security.SHA256Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class ContractRepo_Impl implements ContractRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    SlotRepo slotRepo;

    @Override
    public Contract createAndUpdate(Contract contract) {
        System.out.println("ContractRepo_Impl.createAndUpdate" + contract);
        if(!contract.getStatus().equals("V")// đặt trước
                && !contract.getStatus().equals("Y")// đã thuê
                && !contract.getStatus().equals("C")// đã huỷ
                && !contract.getStatus().equals("R")// đã trả chỗ
                && !contract.getStatus().equals("I")){ // đặt không book trước
            return null;
        }
        return entityManager.merge(contract);
    }

    @Override
    public boolean delete(int id) {
        Contract contract = entityManager.find(Contract.class, id);
        if(contract != null){
            entityManager.remove(contract);
            return true;
        }
        return false;
    }

    @Override
    public List<Contract> findAll() {
        return  entityManager.createQuery("select g from Contract g").getResultList();
    }

    @Override
    public List<Contract> findByUser(User user) {
        Integer userId=user.getId();
        Query query = entityManager
                .createQuery("select u from Contract u where u.userId= :userId");
        List<Contract> contracts = query.setParameter("userId", userId).getResultList();

        return contracts;

    }


}
