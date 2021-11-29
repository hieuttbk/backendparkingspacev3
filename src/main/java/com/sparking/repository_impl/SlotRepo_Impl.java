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
    public List<Slot> getAll(String field) {
        return entityManager.createQuery("select s from Slot s where s.fieldId =: fieldId")
                .setParameter("fieldId", Integer.parseInt(field)).getResultList();
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
