package com.hongdatchy.repository_impl;

import com.hongdatchy.entities.data.Manager;
import com.hongdatchy.entities.payload.LoginForm;
import com.hongdatchy.repository.ManagerRepo;
import com.hongdatchy.repository.UserRepo;
import com.hongdatchy.security.SHA256Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class ManagerRepo_Impl implements ManagerRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    UserRepo userRepo;

    @Override
    public Manager createAndUpdate(Manager manager) {
        List<Manager> oldManagers = entityManager.createQuery("select x from Manager x where x.id =:id")
                .setParameter("id", manager.getId()).getResultList();
        if(userRepo.checkEmailExisted(manager.getEmail())){
            if(oldManagers.size() == 0
                    || !oldManagers.get(0).getId().equals(manager.getId())
                    || !oldManagers.get(0).getEmail().equals(manager.getEmail())){
                System.out.println(oldManagers);
                System.out.println(manager);
                return null;
            }
        }
        manager.setPass(SHA256Service.getSHA256(manager.getPass()));
        return entityManager.merge(manager);
    }

    @Override
    public boolean delete(int id) {
        Manager manager = entityManager.find(Manager.class, id);
        if(manager != null){
            entityManager.createQuery("delete from ManagerField x where x.managerId =:id")
            .setParameter("id", id).executeUpdate();
            entityManager.remove(manager);
            return true;
        }
        return false;
    }

    @Override
    public List<Manager> findAll() {
        return entityManager.createQuery("select x from Manager x").getResultList();
    }

    @Override
    public boolean login(LoginForm loginForm) {
        List<Manager> managers = entityManager
                .createQuery("select m from Manager m where m.email= :email and m.pass = :password")
                .setParameter("email", loginForm.getEmail())
                .setParameter("password", SHA256Service.getSHA256(loginForm.getPassword()))
                .getResultList();
        if(managers.size() != 0){
            managers.get(0).setLastTimeAccess(new Timestamp(new Date().getTime()));
            return true;
        }
        return false;
    }

    @Override
    public Manager findByEmail(String email) {
        List<Manager> managers = entityManager
                .createQuery("select m from Manager m where m.email= :email").setParameter("email", email).getResultList();
        if(managers.size() == 1){
            return managers.get(0);
        }
        return null;
    }
}
