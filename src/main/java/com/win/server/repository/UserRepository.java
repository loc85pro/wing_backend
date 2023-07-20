package com.win.server.repository;

import com.win.server.entity.UserEntity;
import com.win.server.exception.myexception.UserNotFoundException;
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
        return (UserEntity) entityManager.createQuery("FROM UserEntity user WHERE user.id=:id").setParameter("id",id).getSingleResult();
    }

    public UserEntity getByUsername(String username) {
        Object rs = entityManager.createQuery("FROM UserEntity user WHERE user.username=:username").setParameter("username",username).getSingleResult();
        if (rs==null)
            throw new UserNotFoundException(username);
        return (UserEntity)rs;
    }

    public UserEntity getByEmail(String email) {
        Object rs = entityManager.createQuery("FROM UserEntity user WHERE user.email=:email").setParameter("email",email).getSingleResult();
        if (rs==null)
            throw new UserNotFoundException("email: "+email);
        return (UserEntity)rs;
    }

    @Transactional
    public void create(UserEntity userEntity) {
        entityManager.persist(userEntity);
    }
}
