package com.win.server.repository;

import com.win.server.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    @PersistenceContext
    private EntityManager entityManager;
    public UserEntity getById(String id) {
        return entityManager.find(UserEntity.class,id);
    }

    public UserEntity getByUsername(String username) {
        return (UserEntity) entityManager.createQuery("FROM UserEntity user WHERE user.username=:username").setParameter("username",username).getSingleResult();
    }

    @Transactional
    public void create(UserEntity userEntity) {
        entityManager.persist(userEntity);
    }
}
