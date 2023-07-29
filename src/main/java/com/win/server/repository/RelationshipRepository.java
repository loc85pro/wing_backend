package com.win.server.repository;

import com.win.server.entity.RelationshipEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RelationshipRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public RelationshipEntity save(RelationshipEntity entity) {
        entityManager.persist(entity);
        return entity;
    }

    // entityManager.find() will return null in case there aren't result be found
    public RelationshipEntity findById(String id) {
        return entityManager.find(RelationshipEntity.class, id);
    }

    public List<RelationshipEntity> getAllFriend(String user_id) {
        return entityManager.createQuery("FROM RelationshipEntity rel WHERE rel.user_1=:id OR rel.user_2=:id ORDER BY rel.create_at").setParameter("id",user_id).getResultList();
    }

    public RelationshipEntity getRelationShip(String user_1, String user_2) {
        return (RelationshipEntity) entityManager.createQuery("FROM RelationshipEntity rel WHERE (rel.user_1=:id_1 AND rel.user_2=:id_2) OR (rel.user_1=:id_2 AND rel.user_2=:id_1) ORDER BY rel.create_at").setParameter("id_1",user_1).setParameter("id_2",user_2).getSingleResult();
    }

    public RelationshipEntity setNewRelationShip() {
        List<RelationshipEntity> rs = new ArrayList<RelationshipEntity>(entityManager.createQuery("FROM RelationshipEntity rel WHERE rel.status=:status").setParameter("status", "FRIEND").getResultList());
        return null;

    }
}
