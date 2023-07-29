package com.win.server.controller;

import com.win.server.entity.RelationshipEntity;
import com.win.server.repository.RelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/relationship")
@RequiredArgsConstructor
public class RelationshipController {
    private final RelationshipRepository relationshipRepository;

    @PostMapping("/set")
    @ResponseStatus(HttpStatus.CREATED)
    public RelationshipEntity setRelationship() {
        return relationshipRepository.setNewRelationShip();
    }
}
