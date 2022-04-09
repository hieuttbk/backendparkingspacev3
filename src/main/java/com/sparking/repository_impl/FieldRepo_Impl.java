package com.sparking.repository_impl;

import com.sparking.entities.data.*;
import com.sparking.entities.jsonResp.FieldJson;
import com.sparking.repository.AreaRepo;
import com.sparking.repository.DistrictRepo;
import com.sparking.repository.FieldRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class FieldRepo_Impl implements FieldRepo {

    private static Logger logger = LoggerFactory.getLogger(FieldRepo_Impl.class);
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Field createAndUpdate(Field field) {
        return entityManager.merge(field);
    }

    @Override
    public boolean delete(int id) {
        Field field = entityManager.find(Field.class, id);
        if(field != null){

            entityManager.remove(field);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Field> findAll() {
        return entityManager
                .createQuery("select x from Field x").getResultList();
    }

    @Override
    public List<Field> filterByDistrict(int district) {
        ArrayList<Field> fields = new ArrayList<Field>();
        List<Area> listAreas = entityManager
                .createQuery("Select a from Area a where a.idDistrict =:district")
                .setParameter("district", district)
                .getResultList();
        for (int i = 0; i < listAreas.size(); i++) {
            List<Field> listFields = entityManager
                    .createQuery("Select f from Field f where f.idArea =:area")
                    .setParameter("area", listAreas.get(i).getId())
                    .getResultList();
            if (listFields.size() > 0) {
                for (int j = 0; j < listFields.size(); j++) {
                    fields.add(listFields.get(j));
                }
            } else {
                continue;
            }
        }

        return fields;
    }

    @Override
    public List<Field> filterByArea(int area) {
        List<Field> allFields = findAll();
        if (allFields.size() > 0) {
            ArrayList<Field> fields = new ArrayList<Field>();
            for (int i = 0; i < allFields.size(); i++) {
                Field field = allFields.get(i);
                if (field.getIdArea().equals(area)) {
                    fields.add(field);
                }
            }
            return fields;
        }

        return null;
    }

    @Override
    public List<Field> managerFind(Manager manager) {
        List <ManagerField> ManagerFields = entityManager
                .createQuery("select x from ManagerField x where x.managerId =:id")
                .setParameter("id", manager.getId()).getResultList();
        List<Field> fields = new ArrayList<>();
        for (ManagerField managerField: ManagerFields) {
            fields.add(entityManager.find(Field.class, managerField.getFieldId()));
        }
        return fields;
    }

    @Override
    public List<Field> managerFilterByDistrict(int district, Manager manager) {
        int managerId = manager.getId();
        ArrayList<Field> fields = new ArrayList<Field>();
        ArrayList<Integer> idAreas = new ArrayList<Integer>();

        List<Area> areas = entityManager
                .createQuery("Select a from Area a where a.idDistrict =:district")
                .setParameter("district", district)
                .getResultList();
        if (areas.size() > 0) {
            for (int i = 0; i < areas.size(); i++) {
                idAreas.add(areas.get(i).getId());
            }
        } else {
            return null;
        }

        List<ManagerField> managerFields = entityManager
                .createQuery("Select m from ManagerField m where m.managerId =:id")
                .setParameter("id", managerId)
                .getResultList();
        for (ManagerField managerField: managerFields) {
            try {
                List<Field> fieldOfManagers = entityManager
                        .createQuery("Select f from Field f where f.id =:id")
                        .setParameter("id", managerField.getFieldId())
                        .getResultList();
                if (fieldOfManagers.size() > 0) {
                    Field fieldManager = fieldOfManagers.get(0);
                    for (Integer idArea: idAreas) {
                        if (idArea.equals(fieldManager.getIdArea())) {
                            fields.add(fieldManager);
                        } else {
                            continue;
                        }
                    }
                } else {
                    continue;
                }
            } catch (Exception e) {
                if (e instanceof IOException) {
                    throw e;
                }
                FieldRepo_Impl.logger.error("Manager filters not found: " + e);
                return null;
            }
        }
        return fields;
    }

    @Override
    public List<Field> managerFilterByArea(int area, Manager manager) {
        int managerId = manager.getId();
        ArrayList<Field> fields = new ArrayList<Field>();

        List<ManagerField> fieldsOfManager = entityManager
                .createQuery("Select m from ManagerField m where m.managerId =:id")
                .setParameter("id", managerId)
                .getResultList();
        for (ManagerField fieldOfManager: fieldsOfManager) {
            List<Field> filterFieldArea = entityManager
                    .createQuery("Select f from Field f where f.id =:id")
                    .setParameter("id", fieldOfManager.getFieldId())
                    .getResultList();
            if (filterFieldArea.size() > 0) {
                try {
                    for (Field field: filterFieldArea) {
                        if (field.getIdArea().equals(area)) {
                            fields.add(field);
                        } else {
                            continue;
                        }
                    }
                } catch (Exception e) {
                    if (e instanceof IOException) {
                        throw e;
                    }
                    FieldRepo_Impl.logger.error("filterFieldArea wrong: " + e);
                    return null;
                }
            } else {
                continue;
            }
        }
        return fields;
    }

    @Override
    public Field managerUpdate(Field field, Manager manager) {
        if(field == null || entityManager.find(Field.class, field.getId()) == null){
            return null;
        }
        return check(field, manager) ? entityManager.merge(field) : null;
    }

    @Override
    public boolean managerDelete(int id, Manager manager) {
        Field field = entityManager.find(Field.class, id);
        if (field == null) {
            return false;
        }else if(check(field, manager)){
            return delete(id);
        }
        return false;
    }

    @Override
    public Field findById(Integer fieldId) {
        Query query = entityManager
                .createQuery("select u from Field u where u.id= :fieldId");
        List<Field> fields = query.setParameter("fieldId", fieldId).getResultList();
        if(fields.size() == 1){
            return fields.get(0);
        }
        return null;
    }

    boolean check(Field field, Manager manager){
        List<Field> fields = managerFind(manager);
        for (Field f: fields){
            if(f.getId().equals(field.getId())) return true;
        }
        return false;
    }
}
