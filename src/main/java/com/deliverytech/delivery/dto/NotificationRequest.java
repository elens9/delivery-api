package com.deliverytech.delivery.dto;



import com.deliverytech.delivery.entity.NotificationType;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NotificationRequest {

    @NotBlank(message = "Título é obrigatório")
    private String title;

    @NotBlank(message = "Mensagem é obrigatória")
    private String message;

    @NotNull(message = "Tipo é obrigatório")
    private NotificationType type;

    @NotNull(message = "User ID é obrigatório")
    private Long userId;

    private Long pedidoId;
}
