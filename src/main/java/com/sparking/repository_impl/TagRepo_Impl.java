package com.sparking.repository_impl;

import com.sparking.entities.data.Tag;
import com.sparking.entities.data.User;
import com.sparking.repository.TagRepo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional(rollbackFor = Exception.class, timeout = 30000)
@Repository
public class TagRepo_Impl implements TagRepo {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Tag createAndUpdate(Tag tag) {
        List<Tag> tags = entityManager.createQuery("select x from Tag x where x.userId = :id")
                .setParameter("id", tag.getUserId()).getResultList();
        if(tags.size() == 0){
            return entityManager.merge(tag);
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        Tag tag = entityManager.find(Tag.class, id);
        if(tag != null){
            entityManager.detach(tag);
            return true;
        }
        return false;
    }

    @Override
    public List<Tag> findAll() {
        return entityManager.createQuery("select x from Tag x").getResultList();
    }

    @Override
    public Tag findByTagId(String id) {
      //  System.out.println("[Debug] findByTagId " + id);

        Query query = entityManager
                .createQuery("select t from Tag t where t.tagId=:tagId");
        List<Tag> tags = query.setParameter("tagId", id).getResultList();



        if(tags.size() == 1){
            return tags.get(0);
        }
        return null;
    }
}
