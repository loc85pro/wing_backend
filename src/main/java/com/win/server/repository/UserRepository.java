package com.win.server.repository;

import com.win.server.entity.UserEntity;
import com.win.server.exception.myexception.UserNotFoundException;
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
        try {
            return (UserEntity) entityManager.createQuery("FROM UserEntity user WHERE user.email=:email").setParameter("email",email).getSingleResult();
        } catch (Exception ex) {
            throw  new UserNotFoundException();
        }
    }

    public UserEntity findByUsername(String username) {
        try {
            return (UserEntity) entityManager.createQuery("FROM UserEntity user WHERE user.user_name=:username").setParameter("username",username).getSingleResult();
        } catch (Exception ex) {
            throw  new UserNotFoundException();
        }
    }
    public UserEntity findById(String id) {

        try {
            return entityManager.find(UserEntity.class, id);
        } catch (Exception ex) {
            throw  new UserNotFoundException();
        }
    }

    public UserEntity findByPhone(String phone) {
        try {
            return (UserEntity) entityManager.createQuery("FROM UserEntity user WHERE user.phone=:phone").setParameter("phone",phone).getSingleResult();
        } catch (Exception ex) {
            throw  new UserNotFoundException();
        }
    }

    @Transactional
    public void save(UserEntity user) {
        try {
            entityManager.persist(user);
        } catch (Exception ex) {
            System.out.println("This is error: " + ex);
        }

    }
}