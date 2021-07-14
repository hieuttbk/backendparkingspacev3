package com.hongdatchy.repository;

import com.hongdatchy.entities.data.MyPackage;

import java.util.List;

public interface PackageRepo {
    MyPackage create(MyPackage myPackage);

    List<MyPackage> findAll();
}