package com.activitytracker.service;

import com.activitytracker.model.UserActivityEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.activitytracker.config.RabbitMQConfig;
import com.activitytracker.entity.UserActivity;
import com.activitytracker.repository.ActivityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ActivityConsumer {

    @Autowired
    private ActivityRepository repository;
    
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // Subscribing to user_activity_events queue and processing messages
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeMessage(UserActivityEvent event) throws com.fasterxml.jackson.core.JsonProcessingException {
        log.info("Received event: {}", event);
        try {
            UserActivity activity = new UserActivity();
            activity.setUserId(event.getUserId());
            activity.setEventType(event.getEventType());
            activity.setTimestamp(event.getTimestamp());

            activity.setMetadata(objectMapper.writeValueAsString(event.getMetadata()));

            UserActivity saved = repository.save(activity);
            log.info("Saved activity with ID: {}", saved.getId());
        } catch (Exception e) {
            log.error("Error saving activity: {}", e.getMessage(), e);
            // Not throwing 'e' ensures the message is acknowledged and not lost or looped infinitely
        }
    }
}