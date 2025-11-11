package com.deliverytech.delivery.controller;


import com.deliverytech.delivery.dto.WebSocketMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class WebSocketController {

    @MessageMapping("/notifications.subscribe")
    @SendTo("/user/queue/notifications")
    public WebSocketMessage handleSubscription(Principal principal) {
        WebSocketMessage message = new WebSocketMessage();
        message.setType("SUBSCRIPTION_CONFIRMED");
        message.setTitle("Conexão Estabelecida");
        message.setMessage("Você agora receberá notificações em tempo real");
        message.setTimestamp(LocalDateTime.now());
        return message;
    }

    @SubscribeMapping("/topic/notifications")
    public WebSocketMessage onSubscribe() {
        WebSocketMessage message = new WebSocketMessage();
        message.setType("WELCOME");
        message.setTitle("Bem-vindo ao DeliveryTech");
        message.setMessage("Você está conectado ao sistema de notificações");
        return message;
    }
}