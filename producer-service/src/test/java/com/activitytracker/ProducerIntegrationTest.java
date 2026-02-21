package com.activitytracker;

import com.activitytracker.controller.ActivityController;
import com.activitytracker.model.UserActivityEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ProducerIntegrationTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private ActivityController activityController;

    @Test
    void testValidEventIsAcceptedAndPublished() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("browser", "Chrome");

        UserActivityEvent event = new UserActivityEvent();
        event.setUserId(123);
        event.setEventType("login");
        event.setTimestamp(LocalDateTime.now());
        event.setMetadata(metadata);

        ResponseEntity<String> response = activityController.trackEvent(event);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(rabbitTemplate).convertAndSend(eq("user_activity_events"), eq(event));
    }
}
