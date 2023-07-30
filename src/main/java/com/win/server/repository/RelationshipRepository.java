package com.win.server.repository;

import com.win.server.entity.RelationshipEntity;
import com.win.server.exception.myexception.UnknownException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RelationshipRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public RelationshipEntity save(RelationshipEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    // entityManager.find() will return null in case there aren't result be found
    public RelationshipEntity findById(String id) {
        return entityManager.find(RelationshipEntity.class, id);
    }

    public List<RelationshipEntity> getRelationship(String user_id) {
        return entityManager.createQuery("FROM RelationshipEntity rel WHERE rel.user_1=:id OR rel.user_2=:id ORDER BY rel.create_at").setParameter("id",user_id).getResultList();
    }

    public RelationshipEntity getRelationship(String user_1, String user_2) {
        List<RelationshipEntity> rs = entityManager.createQuery("FROM RelationshipEntity rel WHERE (rel.user_1=:id_1 AND rel.user_2=:id_2) OR (rel.user_1=:id_2 AND rel.user_2=:id_1) ORDER BY rel.create_at").setParameter("id_1",user_1).setParameter("id_2",user_2).getResultList();
        switch (rs.size()) {
            case 0:
                return null;
            case 1:
                return rs.get(0);
            default:
                throw new UnknownException();
        }
    }

    @Transactional
    public RelationshipEntity setNewRelationShip(RelationshipEntity entity) {
        List<RelationshipEntity> rs = entityManager.createQuery("FROM RelationshipEntity rel WHERE (rel.user_1=:id_1 AND rel.user_2=:id_2) OR (rel.user_1=:id_2 AND rel.user_2=:id_1)").setParameter("id_1", entity.getUser_1()).setParameter("id_2",entity.getUser_2()).getResultList();
        if (rs.size()>0) {
            rs.forEach((ele) -> {entityManager.remove(ele);});
        }
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    public void remove(RelationshipEntity entity) {
        entityManager.remove(entity);
    }
}
