package com.win.server.repository;

import com.win.server.entity.GoogleEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class GoogleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public GoogleEntity findById( String id) {
        return entityManager.find(GoogleEntity.class, id);
    }

    @Transactional
    public void create(GoogleEntity entity) {
        entityManager.persist(entity);
    }

}
