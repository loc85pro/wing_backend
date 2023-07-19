package com.win.server.repository;

import com.win.server.entity.GoogleEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class GoogleRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private GoogleEntity findById( String id) {
        return entityManager.find(GoogleEntity.class, id);
    }

    private void create(GoogleEntity entity) {
        entityManager.persist(entity);
    }

}
