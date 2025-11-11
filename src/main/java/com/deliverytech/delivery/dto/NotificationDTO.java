package com.deliverytech.delivery.dto;


import com.deliverytech.delivery.entity.NotificationStatus;
import com.deliverytech.delivery.entity.NotificationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private Long id;
    private String title;
    private String message;
    private NotificationType type;
    private NotificationStatus status;
    private Long userId;
    private Long pedidoId;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime readAt;
}