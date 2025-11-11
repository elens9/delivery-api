package com.deliverytech.delivery.service;


import com.deliverytech.delivery.dto.WebSocketMessage;
import com.deliverytech.delivery.entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendNotificationToUser(Notification notification) {
        WebSocketMessage message = new WebSocketMessage();
        message.setType("NOTIFICATION");
        message.setTitle(notification.getTitle());
        message.setMessage(notification.getMessage());
        message.setNotificationType(notification.getType());
        message.setPedidoId(notification.getPedidoId());

        // Envia para o usuário específico
        messagingTemplate.convertAndSendToUser(
                notification.getUserId().toString(),
                "/queue/notifications",
                message
        );

        System.out.println("✅ Notificação enviada via WebSocket para usuário: " + notification.getUserId());
    }

    public void sendPedidoUpdate(Long pedidoId, String status, Long userId) {
        WebSocketMessage message = new WebSocketMessage();
        message.setType("PEDIDO_UPDATE");
        message.setTitle("Atualização do Pedido");
        message.setMessage(String.format("Pedido #%d: %s", pedidoId, status));

        Map<String, Object> data = new HashMap<>();
        data.put("pedidoId", pedidoId);
        data.put("status", status);
        data.put("timestamp", System.currentTimeMillis());
        message.setData(data);

        // Para o cliente
        messagingTemplate.convertAndSendToUser(
                userId.toString(),
                "/queue/pedidos",
                message
        );

        System.out.println("✅ Atualização de pedido enviada via WebSocket: " + pedidoId);
    }
}