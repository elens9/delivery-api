package com.deliverytech.delivery.controller;


import com.deliverytech.delivery.dto.NotificationDTO;
import com.deliverytech.delivery.dto.NotificationRequest;
import com.deliverytech.delivery.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@Valid @RequestBody NotificationRequest request) {
        NotificationDTO notification = notificationService.createNotification(request);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getUserNotifications(@PathVariable Long userId) {
        List<NotificationDTO> notifications = notificationService.getUserNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications(@PathVariable Long userId) {
        List<NotificationDTO> notifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Map<String, Long>> getUnreadCount(@PathVariable Long userId) {
        Long count = notificationService.getUnreadCount(userId);
        return ResponseEntity.ok(Map.of("unreadCount", count));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/user/{userId}/read-all")
    public ResponseEntity<Void> markAllAsRead(@PathVariable Long userId) {
        notificationService.markAllAsRead(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/pedido/{pedidoId}/status")
    public ResponseEntity<Void> sendPedidoStatusUpdate(
            @PathVariable Long pedidoId,
            @RequestParam String status) {

        notificationService.sendPedidoStatusUpdate(pedidoId,
                com.deliverytech.model.NotificationType.ATUALIZACAO_STATUS, status);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/simular-pedido")
    public ResponseEntity<String> simularPedido() {
        notificationService.simularNovoPedido(1L, 1L, new java.math.BigDecimal("45.90"));
        return ResponseEntity.ok("Pedido simulado com sucesso! Notificações serão enviadas automaticamente.");
    }
}