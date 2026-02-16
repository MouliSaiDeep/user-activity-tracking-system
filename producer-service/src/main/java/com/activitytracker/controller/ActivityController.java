package com.activitytracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.activitytracker.model.UserActivityEvent;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/events")
public class ActivityController {

    @PostMapping("/track")
    public ResponseEntity<String> trackEvent(@Valid @RequestBody UserActivityEvent event) {

        // Todo: Enqueue the event to RabbitMQ (not implemented here)

        return ResponseEntity.accepted().body("Event received and enqueued");
    }

}
