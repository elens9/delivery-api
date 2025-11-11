package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Notification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Async
    public void sendNotificationEmail(Notification notification) {
        try {
            // Simulação de envio de email
            System.out.println("📧 [SIMULAÇÃO] Email enviado para usuário " + notification.getUserId());
            System.out.println("📧 Assunto: " + notification.getTitle());
            System.out.println("📧 Mensagem: " + notification.getMessage());
            System.out.println("✅ Email simulado com sucesso!");
        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar email: " + e.getMessage());
        }
    }
}
