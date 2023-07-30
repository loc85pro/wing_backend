package com.win.server.repository;

import com.win.server.entity.CommentEntity;
import com.win.server.entity.PostEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void savePost(PostEntity entity) {
        entityManager.persist(entity);
    }

    public PostEntity findById(String id) {
        return entityManager.find(PostEntity.class, id);
    }

    public List<PostEntity> findByOwner(String id) {
        return new ArrayList<PostEntity>(entityManager.createQuery("FROM PostEntity post WHERE post.owner_id=:id").setParameter("id",id).getResultList());
    }

    @Transactional
    public void remove(PostEntity entity) {
        entityManager.remove(entity);
    }

    @Transactional
    public void update(PostEntity entity) {
        entityManager.persist(entity);
    }
}
