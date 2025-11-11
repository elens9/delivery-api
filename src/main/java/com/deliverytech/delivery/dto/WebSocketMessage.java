package com.deliverytech.delivery.dto;

import com.deliverytech.delivery.entity.NotificationType;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class WebSocketMessage {
    private String type;
    private String title;
    private String message;
    private NotificationType notificationType;
    private Long pedidoId;
    private LocalDateTime timestamp;
    private Map<String, Object> data;

    public WebSocketMessage() {
        this.timestamp = LocalDateTime.now();
    }

    public WebSocketMessage(String type, String title, String message, NotificationType notificationType) {
        this();
        this.type = type;
        this.title = title;
        this.message = message;
        this.notificationType = notificationType;
    }
}