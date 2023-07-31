package com.win.server.service;

import com.win.server.DTO.UserDTO;
import com.win.server.DTO.relationship.RelationshipElementDTO;
import com.win.server.entity.RelationshipEntity;
import com.win.server.exception.myexception.NotFoundException;
import com.win.server.repository.RelationshipRepository;
import com.win.server.security.ContextUserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RelationshipService {
    private final RelationshipRepository relationshipRepository;
    private final UserService userService;

    public List<RelationshipElementDTO> getListFriend() {
        return getListFriendship();
    }

    public RelationshipEntity setNewRelationship(String user_id, String status) {
        RelationshipEntity entity = new RelationshipEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setStatus(status);
        entity.setUser_1(ContextUserManager.getUserId());
        entity.setUser_2(user_id);
        entity.setCreate_at(new Timestamp(System.currentTimeMillis()));
        return relationshipRepository.setNewRelationShip(entity);
    }

    public RelationshipEntity acceptFriendRequest(String user_id) {
        RelationshipEntity current_relationship = getRelationship(ContextUserManager.getUserId(), user_id);
        if (current_relationship==null)
            throw new NotFoundException();
        if (current_relationship.getUser_1().equals(ContextUserManager.getUserId()))
            throw new NotFoundException();
        else {
            relationshipRepository.remove(current_relationship);
            return setNewRelationship(user_id, "FRIEND");
        }
    }
    public boolean unfriend(String user_id) {
        RelationshipEntity current_relationship = getRelationship(ContextUserManager.getUserId(), user_id);
        if (current_relationship == null)
            throw new NotFoundException();
        String status = current_relationship.getStatus();
        if (status.equals("FRIEND") || status.equals("CLOSE_FRIEND")) {
            relationshipRepository.remove(current_relationship);
            return true;
        }
        return false;
    }

    public boolean cancelRequest(String user_id) {
        RelationshipEntity current_relationship = getRelationship(ContextUserManager.getUserId(), user_id);
        if (current_relationship == null)
            throw new NotFoundException();
        String status = current_relationship.getStatus();
        if (status.equals("PENDING")) {
            relationshipRepository.remove(current_relationship);
            return true;
        }
        return false;
    }

    public RelationshipEntity getRelationship(String user_1, String user_2) {
        return relationshipRepository.getRelationship(user_1,user_2);
    }
    //----------------------------------------



    public List<RelationshipEntity> getRelationship(String user_id) {
        return relationshipRepository.getRelationship(user_id);
    }

    private List<RelationshipElementDTO> getListFriendship() {
        String current_user = ContextUserManager.getUserId();
        List<RelationshipEntity> allRelationship = getRelationship(ContextUserManager.getUserId());
        List<RelationshipEntity> friendRelationships = allRelationship.stream().filter(rel -> rel.getStatus().equals("FRIEND") || rel.getStatus().equals("CLOSE_FRIEND")).toList();
        List<RelationshipElementDTO> result = new ArrayList<RelationshipElementDTO>();
        friendRelationships.forEach(rel -> {
            String partner_id = current_user.equals(rel.getUser_1()) ? rel.getUser_2() : rel.getUser_1();
            UserDTO partner = userService.getUserById(partner_id);
            RelationshipElementDTO temp = new RelationshipElementDTO();
            temp.setId(rel.getId());
            temp.setUser_id(partner_id);
            temp.setUser_name(partner.getUser_name());
            temp.setUser_full_name(partner.getFull_name());
            temp.setStatus(rel.getStatus());
            temp.setCreate_at(rel.getCreate_at());
            temp.setAvatar(partner.getAvatarURL());
            result.add(temp);
        });
        return result;
    }
}
