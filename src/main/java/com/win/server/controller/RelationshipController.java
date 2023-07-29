package com.win.server.controller;

import com.win.server.constant.RelationshipStatus;
import com.win.server.entity.RelationshipEntity;
import com.win.server.repository.RelationshipRepository;
import com.win.server.service.RelationshipService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
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
}
