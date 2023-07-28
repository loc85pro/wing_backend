package com.win.server.repository;

import com.win.server.entity.CommentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public CommentEntity saveComment(CommentEntity entity) {
        entityManager.persist(entity);
        return getCommentById(entity.getId());
    }

    public CommentEntity getCommentById(String id) {
        return entityManager.find(CommentEntity.class, id);
    }

    public List<CommentEntity> getAllCommentByPost(String id) {
        return entityManager.createQuery("FROM CommentEntity com WHERE  com.post_id=:id").setParameter("id",id).getResultList();
    }
}
