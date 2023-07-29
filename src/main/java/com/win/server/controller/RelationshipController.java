package com.win.server.controller;

import com.win.server.constant.RelationshipStatus;
import com.win.server.entity.RelationshipEntity;
import com.win.server.repository.RelationshipRepository;
import com.win.server.service.RelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/relationship")
@RequiredArgsConstructor
public class RelationshipController {
    private final RelationshipService relationshipService;

    @PostMapping("/set")
    @ResponseStatus(HttpStatus.CREATED)
    public RelationshipEntity setRelationship(@RequestParam String user_id, @RequestParam RelationshipStatus status) {
        return relationshipService.setNewRelationship(user_id, status.toString());
    }
}
