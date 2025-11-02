package com.deliverytech.delivery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedidos")

public class Pedido {
    private Long id;
    private String pedido;
    private int quantidade;
    private String cliente;
    private Double valor;


    //data do pedido
    @Column(name = "data_pedido")
    private LocalDateTime dataPedido;

    //pedido ativo
    @Column(nullable = true)
    private boolean aberto, finalizado;

    public void finalizar(){
        this.aberto = false;
    }
}
