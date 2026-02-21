package com.activitytracker;

import com.activitytracker.entity.UserActivity;
import com.activitytracker.model.UserActivityEvent;
import com.activitytracker.repository.ActivityRepository;
import com.activitytracker.service.ActivityConsumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ConsumerIntegrationTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityConsumer activityConsumer;

    @Test
    void testMessageIsConsumedAndSavedToDatabase() throws Exception {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("page", "/home");

        UserActivityEvent event = new UserActivityEvent();
        event.setUserId(999);
        event.setEventType("page_view");
        event.setTimestamp(LocalDateTime.now());
        event.setMetadata(metadata);

        activityConsumer.consumeMessage(event);

        ArgumentCaptor<UserActivity> captor = ArgumentCaptor.forClass(UserActivity.class);
        verify(activityRepository).save(captor.capture());
        
        UserActivity savedActivity = captor.getValue();
        assertEquals(999, savedActivity.getUserId());
        assertEquals("page_view", savedActivity.getEventType());
        assertNotNull(savedActivity.getMetadata());
    }
}
