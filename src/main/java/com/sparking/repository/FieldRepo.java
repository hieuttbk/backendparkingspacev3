package com.sparking.repository;

import com.sparking.entities.data.Field;
import com.sparking.entities.data.Manager;

import java.util.List;

public interface FieldRepo{

    Field createAndUpdate(Field field);

    boolean delete(int id);

    List<Field> findAll();

    List<Field> managerFind(Manager manager);

    Field managerUpdate(Field field, Manager manager);

    boolean managerDelete(int id, Manager manager);

    Field findById(Integer fieldId);
}
