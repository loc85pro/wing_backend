package com.win.server.repository;

import com.win.server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,String> {
    public UserEntity findByEmail(String email);

    @Query("FROM UserEntity user WHERE user.user_name=:username")
    public UserEntity findByUser_name(@Param("username") String username);

    @Query("FROM UserEntity user WHERE user.id=:id")
    public UserEntity getById(@Param("id") String id);
}