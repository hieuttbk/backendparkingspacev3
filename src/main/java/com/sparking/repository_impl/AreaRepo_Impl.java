package com.sparking.repository_impl;

import com.sparking.entities.data.Area;
import com.sparking.entities.data.Field;
import com.sparking.entities.data.Manager;
import com.sparking.entities.data.ManagerField;
import com.sparking.repository.AreaRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AreaRepo_Impl implements AreaRepo {
    private final static Logger logger = LoggerFactory.getLogger(AreaRepo_Impl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Area> getAllAreas() {
        return entityManager.createQuery("Select a from Area a").getResultList();
    }

    @Override
    public Area getAreaById(int area) {
        List<Area> areas = entityManager
                .createQuery("Select a from Area a where a.id =:area")
                .setParameter("area", area)
                .getResultList();
        return areas.get(0);
    }

    @Override
    public List<Area> mnGetAllAreas(Manager manager) {
        int managerId = manager.getId();
        List<ManagerField> managerFields = entityManager
                .createQuery("Select m from ManagerField m where m.managerId =:id")
                .setParameter("id", managerId)
                .getResultList();
        ArrayList<Integer> fieldIds = new ArrayList<Integer>();
        if (managerFields.size() > 0) {
            for (ManagerField managerField: managerFields) {
                if (managerField.getFieldId() != null) {
                    if (fieldIds.contains(managerField.getFieldId())) {
                        continue;
                    } else {
                        fieldIds.add(managerField.getFieldId());
                    }
                } else {
                    continue;
                }
            }
        } else {
            this.logger.info("Manager unregistered fields");
            return null;
        }
        ArrayList<Area> areas = new ArrayList<Area>();
        ArrayList<Integer> areaIds = new ArrayList<Integer>();

        for (int fieldId: fieldIds) {
            List<Field> fields = entityManager
                    .createQuery("Select f from Field f where f.id =:id")
                    .setParameter("id", fieldId)
                    .getResultList();
            Field field = fields.get(0);

            try {
                if (field != null) {
                    if (areaIds.contains(field.getIdArea())) {
                        continue;
                    } else {
                        areaIds.add(field.getIdArea());
                    }
                } else {
                    this.logger.info("ManagerField null");
                    return null;
                }
            } catch (Exception e) {
                this.logger.error("Get Field error: " + e.toString());
                return null;
            }
        }

        if (areaIds.size() > 0) {
            for (int areaId: areaIds) {
                List<Area> areaManager = entityManager.createQuery("Select a from Area a where a.id =:id")
                        .setParameter("id", areaId).getResultList();
                if (areaManager == null) {
                    continue;
                } else {
                    areas.add(areaManager.get(0));
                }
            }
        } else {
            this.logger.error("Area null");
            return null;
        }
        return areas;
    }
}
