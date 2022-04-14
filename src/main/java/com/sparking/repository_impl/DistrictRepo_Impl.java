package com.sparking.repository_impl;

import com.sparking.entities.data.Area;
import com.sparking.entities.data.District;
import com.sparking.entities.data.Manager;
import com.sparking.repository.AreaRepo;
import com.sparking.repository.DistrictRepo;
import com.sparking.repository.ManagerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DistrictRepo_Impl implements DistrictRepo {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private AreaRepo areaRepo;

    @Override
    public List<District> getAllDistrict() {
        return entityManager.createQuery("Select d from District d").getResultList();
    }

    @Override
    public District getDistrictByID(int district) {
        List<District> districts = entityManager
                .createQuery("Select d from District d where d.id =:id").setParameter("id", district).getResultList();
        return districts.get(0);
    }

    @Override
    public List<District> mnGetAllDistricts(Manager manager) {
        List<Area> areas = areaRepo.mnGetAllAreas(manager);

        ArrayList<District> districts = new ArrayList<District>();

        for (Area area: areas) {
            List<District> listDistricts = entityManager.createQuery("Select d from District d where d.id =:id")
                    .setParameter("id", area.getIdDistrict()).getResultList();
            if (listDistricts.size() > 0) {
                districts.add(listDistricts.get(0));
            } else {
                continue;
            }
        }

        return districts;
    }
}
