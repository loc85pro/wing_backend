package com.win.server.controller;

import com.win.server.DTO.auth.SimpleMessage;
import com.win.server.constant.RelationshipStatus;
import com.win.server.entity.RelationshipEntity;
import com.win.server.exception.myexception.NotFoundException;
import com.win.server.security.ContextUserManager;
import com.win.server.service.RelationshipService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

@RestController
@RequestMapping("/relationship")
@RequiredArgsConstructor
@Tag( name = "Relationship")
public class RelationshipController {
    private final RelationshipService relationshipService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RelationshipEntity getRelationship(@RequestParam String user_id) {
        RelationshipEntity rs = relationshipService.getRelationship(ContextUserManager.getUserId(), user_id);
        if (rs==null)
            return new RelationshipEntity();
        return rs;
    }

    @PostMapping ("/request")
    public ResponseEntity<RelationshipEntity> sendAddFriendRequest(@RequestParam String user_id) {
        RelationshipEntity current_relationship = relationshipService.getRelationship(ContextUserManager.getUserId(), user_id);
        if (current_relationship==null)
            return ResponseEntity.status(200).body(relationshipService.setNewRelationship(user_id, "PENDING"));
        else
            return ResponseEntity.status(409).body(current_relationship);
    }

    @PostMapping("/accept")
    public RelationshipEntity acceptRequest(@RequestParam String user_id) {
        return relationshipService.acceptFriendRequest(user_id);
    }

    @DeleteMapping
    public ResponseEntity<SimpleMessage> unfriend(@RequestParam String user_id) {
        boolean isDone = relationshipService.unfriend(user_id);
        SimpleMessage response = new SimpleMessage();
        response.setCode(isDone ? 200 : 400);
        response.setMessage(isDone ? "Done" : "Error");
        response.setDetail(isDone ? "" : "Unfriend only available for FRIEND and CLOSE_FRIEND relationships");
        return ResponseEntity.status(isDone ? 200 : 400).body(response);
    }
}
