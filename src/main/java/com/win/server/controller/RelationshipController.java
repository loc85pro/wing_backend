package com.win.server.controller;

import com.win.server.constant.RelationshipStatus;
import com.win.server.entity.RelationshipEntity;
import com.win.server.security.ContextUserManager;
import com.win.server.service.RelationshipService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/relationship")
@RequiredArgsConstructor
@Tag( name = "Relationship")
public class RelationshipController {
    private final RelationshipService relationshipService;

    @PostMapping("/set")
    @ResponseStatus(HttpStatus.CREATED)
    public RelationshipEntity setRelationship(@RequestParam String user_id, @RequestParam RelationshipStatus status) {
        return relationshipService.setNewRelationship(user_id, status.toString());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RelationshipEntity getRelationship(@RequestParam String user_id) {
        RelationshipEntity rs = relationshipService.getRelationship(ContextUserManager.getUserId(), user_id);
        if (rs==null)
            return new RelationshipEntity();
        return rs;
    }
}
