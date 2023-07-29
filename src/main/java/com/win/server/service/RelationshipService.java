package com.win.server.service;

import com.win.server.entity.RelationshipEntity;
import com.win.server.repository.RelationshipRepository;
import com.win.server.security.ContextUserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RelationshipService {
    private final RelationshipRepository relationshipRepository;

    public RelationshipEntity setNewRelationship(String user_id, String status) {
        RelationshipEntity entity = new RelationshipEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setStatus(status);
        entity.setUser_1(ContextUserManager.getUserId());
        entity.setUser_2(user_id);
        entity.setCreate_at(new Timestamp(System.currentTimeMillis()));
        return relationshipRepository.setNewRelationShip(entity);
    }

    public RelationshipEntity getRelationship(String user_1, String user_2) {
        return relationshipRepository.getRelationShip(user_1,user_2);
    }
}
