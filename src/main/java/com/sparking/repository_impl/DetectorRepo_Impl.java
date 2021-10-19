package com.sparking.repository_impl;

import com.sparking.entities.data.Detector;
import com.sparking.entities.data.Gateway;
import com.sparking.entities.data.Manager;
import com.sparking.entities.payloadReq.UpdateSlotIdPayload;
import com.sparking.repository.DetectorRepo;
import com.sparking.repository.GatewayRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class DetectorRepo_Impl implements DetectorRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    GatewayRepo gatewayRepo;

    @Override
    public Detector createAndUpdate(Detector detector) {
        return entityManager.merge(detector);
    }

    @Override
    public boolean delete(int id) {
        Detector detector = entityManager.find(Detector.class, id);
        if(detector != null){
            entityManager.remove(detector);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Detector> findAll() {
        return entityManager.createQuery("select d from Detector d").getResultList();
    }

    @Override
    public List<Detector> findBySlotId(int id) {
        return entityManager.createQuery("select d from Detector d where d.slotId = :id")
        .setParameter("id", id).getResultList();
    }

    @Override
    public List<Detector> managerFind(Manager manager) {
        List<Gateway> gateways = gatewayRepo.managerFind(manager);
        List<Detector> detectors = new ArrayList<>();
        for(Gateway gateway: gateways){
            detectors.addAll(entityManager.createQuery("select x from Detector x where x.gatewayId =:id")
                    .setParameter("id", gateway.getId()).getResultList());
        }
        return detectors;
    }

    @Override
    public Detector managerCreateAndUpdate(Detector detector, Manager manager) {
        return check(detector, manager) ? entityManager.merge(detector) : null;
    }

    @Override
    public Detector updateSlotId(UpdateSlotIdPayload updateSlotIdPayload) {
        String addressDetector = updateSlotIdPayload.getAddressDetector();
        int gatewayId = updateSlotIdPayload.getGatewayId();
        int slotId = updateSlotIdPayload.getSlotId();

        List<Detector> detectors = entityManager
                .createQuery("select d from Detector d where d.addressDetector =: add and d.gatewayId =: gwId")
                .setParameter("add", addressDetector).setParameter("gwId", gatewayId).getResultList();

        Detector detector = detectors.get(0);
        if (detector.getSlotId().equals(slotId)) {
            return null;
        }
        detector.setSlotId(slotId);
        entityManager.merge(detector);
        return detector;
    }

    @Override
    public boolean managerDelete(int id, Manager manager) {
        Detector detector = entityManager.find(Detector.class, id);
        if(detector == null){
            return false;
        }else if(check(detector, manager)){
            entityManager.remove(detector);
        }
        return false;
    }

    @Override
    public Detector findById(int id) {
        return entityManager.find(Detector.class, id);
    }

    @Override
    public Detector managerFindById(int id, Manager manager) {
        Detector detector = entityManager.find(Detector.class, id);
        return check(detector, manager) ? detector : null;
    }

    boolean check(Detector detector, Manager manager){
        List<Detector> detectors = managerFind(manager);
        for(Detector d: detectors){
            if(d.getId().equals(detector.getId())) return true;
        }
        return false;
    }



}
