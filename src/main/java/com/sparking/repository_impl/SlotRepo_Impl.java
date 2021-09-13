package com.sparking.repository_impl;

import com.sparking.entities.data.Slot;
import com.sparking.repository.SlotRepo;
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

    @Override
    public Slot createAndUpdate(Slot slot) {
        return entityManager.merge(slot);
    }

    @Override
    public boolean delete(int id) {
        Slot slot = entityManager.find(Slot.class, id);
        if(slot != null){
            entityManager.createQuery("delete from Detector x where x.slotId =:id")
            .setParameter("id", id).executeUpdate();

            entityManager.remove(slot);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Slot> findAll() {
        return entityManager.createQuery("select s from Slot s").getResultList();
    }

    @Override
    public Slot findById(int id){
        return entityManager.find(Slot.class, id);
    }

}
