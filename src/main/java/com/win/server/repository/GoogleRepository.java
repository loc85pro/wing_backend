package com.win.server.repository;

import com.win.server.entity.GoogleEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoogleRepository extends JpaRepository<GoogleEntity, String> {
}
