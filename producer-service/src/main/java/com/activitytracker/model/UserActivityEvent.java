package com.activitytracker.model;

import java.time.LocalDateTime;
import java.util.Map;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityEvent {
    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotNull(message = "Event type is required")
    private String event_type;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;

    private Map<String, Object> metadata;
}