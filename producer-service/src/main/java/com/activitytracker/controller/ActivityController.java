package com.activitytracker.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.activitytracker.config.RabbitMQConfig;
import com.activitytracker.model.UserActivityEvent;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/events")
public class ActivityController {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/track")
    public ResponseEntity<String> trackEvent(@Valid @RequestBody UserActivityEvent event) {

        // Publish the message to the queue
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, event);

        return ResponseEntity.accepted().body("Event received and enqueued");
    }

}
