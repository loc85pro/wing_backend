package com.win.server.repository;

import com.win.server.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager entityManager;
    public UserEntity getByUsername(String username) {
        return entityManager.find(UserEntity.class,username);
    }
}
