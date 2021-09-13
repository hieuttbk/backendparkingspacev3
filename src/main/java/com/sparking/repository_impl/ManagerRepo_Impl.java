package com.sparking.repository_impl;

import com.sparking.entities.data.Manager;
import com.sparking.entities.payload.LoginForm;
import com.sparking.repository.ManagerRepo;
import com.sparking.repository.UserRepo;
import com.sparking.security.SHA256Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public Manager login(LoginForm loginForm) {
        List<Manager> managers = entityManager
                .createQuery("select m from Manager m where m.email= :email and m.pass = :password")
                .setParameter("email", loginForm.getEmail())
                .setParameter("password", SHA256Service.getSHA256(loginForm.getPassword()))
                .getResultList();
        if(managers.size() != 0){
            managers.get(0).setLastTimeAccess(new Timestamp(new Date().getTime()));
            return managers.get(0);
        }
        return null;
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

    @Override
    public Manager findById(int id) {
        return entityManager.find(Manager.class, id);
    }
}
