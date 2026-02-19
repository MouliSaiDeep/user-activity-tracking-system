package com.activitytracker.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityEvent {
    @NotNull(message = "User ID is required")
    @JsonProperty("user_id")
    private Integer userId;

    @NotNull(message = "Event type is required")
    @JsonProperty("event_type")
    private String event_type;

    @NotNull(message = "Timestamp is required")
    private LocalDateTime timestamp;

    private Map<String, Object> metadata;
}