package com.win.server.repository;

import com.win.server.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.naming.CommunicationException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository  {
    private final EntityManager entityManager;
    public UserEntity findByEmail(String email) {
        return (UserEntity) entityManager.createQuery("FROM UserEntity user WHERE user.email=:email").setParameter("email",email).getSingleResult();
    }

    public UserEntity findByUsername(String username) {
        return (UserEntity) entityManager.createQuery("FROM UserEntity user WHERE user.user_name=:username").setParameter("username",username).getSingleResult();
    }

    public UserEntity findById(String id) {
        return entityManager.find(UserEntity.class, id);
    }

    public UserEntity findByPhone(String phone) {
        return (UserEntity) entityManager.createQuery("FROM UserEntity user WHERE user.phone=:phone").setParameter("phone",phone).getSingleResult();
    }

    @Transactional
    public void save(UserEntity user) {
            entityManager.persist(user);
    }
}