package com.sparking.repository_impl;

import com.sparking.entities.data.Field;
import com.sparking.entities.data.Manager;
import com.sparking.entities.data.Slot;
import com.sparking.repository.FieldRepo;
import com.sparking.repository.SlotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class SlotRepo_Impl implements SlotRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    FieldRepo fieldRepo;

    @Override
    public Slot createAndUpdate(Slot slot) {
        return entityManager.merge(slot);
    }

    @Override
    public boolean delete(int id) {
        Slot slot = entityManager.find(Slot.class, id);
        if(slot != null){

            entityManager.remove(slot);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Slot> getByQuantity(String field, String quantity) {
        int query = Integer.parseInt(quantity);
        List<Slot> slots = entityManager.createQuery("Select s from Slot s where s.fieldId =: fieldId")
                .setParameter("fieldId", Integer.parseInt(field)).setMaxResults(query).getResultList();
        return slots;
    }

    @Override
    public List<Slot> getAll(String field) {
//        Date date = new Date();
//        System.out.println("PrevDate - " + date);
        List<Slot> slots = entityManager.createQuery("select s from Slot s where s.fieldId =: fieldId")
                .setParameter("fieldId", Integer.parseInt(field)).getResultList();
//        if (slots.size() > 0) {
//            Date newDate = new Date();
//            System.out.println("NextDate - " + newDate);
//        }
        return slots;
    }

    @Override
    public List<Slot> findAll() {
        return entityManager.createQuery("select s from Slot s").getResultList();
    }

    @Override
    public Slot findById(int id){
        return entityManager.find(Slot.class, id);
    }

    @Override
    public Slot managerCreateAndUpdate(Manager manager, Slot slot) {
        if(check(manager, slot)){
            return createAndUpdate(slot);
        }else{
            return null;
        }
    }

    @Override
    public boolean managerDelete(Manager manager, int id) {
        Slot slot = findById(id);
        if(slot == null){
            return false;
        }
        if(check(manager, slot)){
            return delete(id);
        }else{
            return false;
        }
    }

    boolean check(Manager manager, Slot slot){
        List<Field> fieldsOfThisManager = fieldRepo.managerFind(manager);
        for (Field field : fieldsOfThisManager){
            if (field.getId().equals(slot.getFieldId())){
                return true;
            }
        }
        return false;
    }

}
