package com.deliverytech.delivery.entity;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataPedido = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private StatusPedido status = StatusPedido.PENDENTE;

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    @Column(nullable = false)
    private Long clienteId;

    @Column(nullable = false)
    private Long restauranteId;

    private String enderecoEntrega;

    @PrePersist
    protected void onCreate() {
        if (dataPedido == null) {
            dataPedido = LocalDateTime.now();
        }
    }
}

enum StatusPedido {
    PENDENTE, CONFIRMADO, PREPARANDO, SAIU_PARA_ENTREGA, ENTREGUE, CANCELADO
}