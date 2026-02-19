package com.activitytracker.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.activitytracker.config.RabbitMQConfig;
import com.activitytracker.entity.UserActivity;
import com.activitytracker.repository.ActivityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ActivityConsumer {

    @Autowired
    private ActivityRepository repository;
    
    @Autowired
    private ObjectMapper objectMapper;

    // Subscribing to user_activity_events queue and processing messages
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumeMessage(UserActivityEvent event) throws com.fasterxml.jackson.core.JsonProcessingException {
            UserActivity activity = new UserActivity();
            activity.setUserId(event.getUserId());
            activity.setEventType(event.getEventType());
            activity.setTimestamp(event.getTimestamp());

            activity.setMetadata(objectMapper.writeValueAsString(event.getMetadata()));

            repository.save(activity);
        } catch (Exception e) {
            System.err.println("Error processing event: " + e.getMessage());
        }
    }