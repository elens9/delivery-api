package com.deliverytech.delivery.service;

import com.deliverytech.delivery.dto.NotificationDTO;
import com.deliverytech.delivery.dto.NotificationRequest;
import com.deliverytech.delivery.entity.*;
import com.deliverytech.delivery.repository.NotificationRepository;
import com.deliverytech.delivery.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private EmailService emailService;

    @Transactional
    public NotificationDTO createNotification(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());
        notification.setUserId(request.getUserId());
        notification.setPedidoId(request.getPedidoId());

        Notification saved = notificationRepository.save(notification);

        // Envia via WebSocket
        webSocketService.sendNotificationToUser(saved);

        // Envia email (assíncrono)
        if (shouldSendEmail(saved.getType())) {
            emailService.sendNotificationEmail(saved);
        }

        return convertToDTO(saved);
    }

    public List<NotificationDTO> getUserNotifications(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<NotificationDTO> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndStatusOrderByCreatedAtDesc(userId, NotificationStatus.UNREAD)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void markAsRead(Long notificationId) {
        notificationRepository.markAsRead(notificationId, NotificationStatus.READ, LocalDateTime.now());

        // Notifica via WebSocket que a notificação foi lida
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification != null) {
            messagingTemplate.convertAndSendToUser(
                    notification.getUserId().toString(),
                    "/queue/notifications/read",
                    Map.of("notificationId", notificationId, "status", "READ")
            );
        }
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        notificationRepository.markAllAsReadByUser(userId, NotificationStatus.READ, NotificationStatus.UNREAD);

        // Notifica via WebSocket
        messagingTemplate.convertAndSendToUser(
                userId.toString(),
                "/queue/notifications/all-read",
                Map.of("userId", userId, "timestamp", LocalDateTime.now())
        );
    }

    public Long getUnreadCount(Long userId) {
        return notificationRepository.countByUserIdAndStatus(userId, NotificationStatus.UNREAD);
    }

    public void sendPedidoStatusUpdate(Long pedidoId, NotificationType type, String status) {
        // Busca o pedido para obter informações
        Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);

        if (pedido != null) {
            String title = "Atualização do Pedido";
            String message = String.format("Seu pedido #%d está: %s", pedidoId, status);

            NotificationRequest request = new NotificationRequest();
            request.setTitle(title);
            request.setMessage(message);
            request.setType(type);
            request.setPedidoId(pedidoId);
            request.setUserId(pedido.getClienteId());

            createNotification(request);

            // Também envia atualização via WebSocket para tracking
            webSocketService.sendPedidoUpdate(pedidoId, status, pedido.getClienteId());
        }
    }

    // Método para simular criação de pedido e notificações automáticas
    @Transactional
    public void simularNovoPedido(Long clienteId, Long restauranteId, java.math.BigDecimal total) {
        // Cria pedido
        Pedido pedido = new Pedido();
        pedido.setClienteId(clienteId);
        pedido.setRestauranteId(restauranteId);
        pedido.setTotal(total);
        pedido.setStatus(StatusPedido.CONFIRMADO);
        Pedido savedPedido = pedidoRepository.save(pedido);

        // Envia notificações automáticas
        sendPedidoStatusUpdate(savedPedido.getId(), NotificationType.PEDIDO_CONFIRMADO, "Confirmado");

        // Simula preparação após 2 minutos
        new Thread(() -> {
            try {
                Thread.sleep(10000); // 10 segundos para teste
                pedido.setStatus(StatusPedido.PREPARANDO);
                pedidoRepository.save(pedido);
                sendPedidoStatusUpdate(savedPedido.getId(), NotificationType.PEDIDO_EM_PREPARO, "Em preparo");

                Thread.sleep(10000); // 10 segundos para teste
                pedido.setStatus(StatusPedido.SAIU_PARA_ENTREGA);
                pedidoRepository.save(pedido);
                sendPedidoStatusUpdate(savedPedido.getId(), NotificationType.PEDIDO_SAIU_PARA_ENTREGA, "Saiu para entrega");

                Thread.sleep(10000); // 10 segundos para teste
                pedido.setStatus(StatusPedido.ENTREGUE);
                pedidoRepository.save(pedido);
                sendPedidoStatusUpdate(savedPedido.getId(), NotificationType.PEDIDO_ENTREGUE, "Entregue");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    private boolean shouldSendEmail(NotificationType type) {
        return List.of(
                NotificationType.PEDIDO_CONFIRMADO,
                NotificationType.PEDIDO_CANCELADO,
                NotificationType.PAGAMENTO_CONFIRMADO,
                NotificationType.PAGAMENTO_RECUSADO
        ).contains(type);
    }

    private NotificationDTO convertToDTO(Notification notification) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(notification.getId());
        dto.setTitle(notification.getTitle());
        dto.setMessage(notification.getMessage());
        dto.setType(notification.getType());
        dto.setStatus(notification.getStatus());
        dto.setUserId(notification.getUserId());
        dto.setPedidoId(notification.getPedidoId());
        dto.setCreatedAt(notification.getCreatedAt());
        dto.setReadAt(notification.getReadAt());
        return dto;
    }
}
