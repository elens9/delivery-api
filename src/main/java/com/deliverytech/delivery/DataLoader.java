package com.deliverytech.delivery;


import com.deliverytech.delivery.entity.*;
import com.deliverytech.delivery.repository.*;
import com.deliverytech.delivery.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== CARREGANDO DADOS DE TESTE ===");

        // Criar clientes
        Cliente cliente1 = new Cliente();
        cliente1.setNome("João Silva");
        cliente1.setEmail("joao@email.com");
        cliente1.setTelefone("(11) 99999-9999");
        clienteRepository.save(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Maria Santos");
        cliente2.setEmail("maria@email.com");
        cliente2.setTelefone("(11) 88888-8888");
        clienteRepository.save(cliente2);

        // Criar restaurantes
        Restaurante restaurante1 = new Restaurante();
        restaurante1.setNome("Pizzaria Bella Napoli");
        restaurante1.setCategoria("PIZZA");
        restaurante1.setTaxaEntrega(new BigDecimal("8.50"));
        restaurante1.setTempoEntregaMinutos(30);
        restauranteRepository.save(restaurante1);

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNome("Hamburgueria Artesanal");
        restaurante2.setCategoria("HAMBURGUER");
        restaurante2.setTaxaEntrega(new BigDecimal("5.00"));
        restaurante2.setTempoEntregaMinutos(25);
        restauranteRepository.save(restaurante2);

        // Criar notificações de exemplo
        notificationService.createNotification(new com.deliverytech.delivery.dto.NotificationRequest(
                "Bem-vindo ao DeliveryTech!",
                "Sua conta foi criada com sucesso!",
                NotificationType.PROMOCAO,
                1L,
                null
        ));

        System.out.println("✅ Dados de teste carregados com sucesso!");
        System.out.println("🚀 Serviço de notificações pronto!");
        System.out.println("📧 Use POST /api/notifications/simular-pedido para testar notificações automáticas");
    }
}
