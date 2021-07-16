package com.hongdatchy.repository;

import com.hongdatchy.entities.data.Field;
import com.hongdatchy.entities.data.Manager;

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
