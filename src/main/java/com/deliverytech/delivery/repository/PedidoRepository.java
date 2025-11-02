package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    //buscar pedidos por cliente
    List<Pedido> findByClienteContainingIgnoreCase(String cliente);

    //buscar pedidos por status aberto
    List<Pedido> findByAberto();

    //buscar pedidos por status finalizado
    List<Pedido> findByFinalizado();

    //buscar pedidos por data
    List<Pedido> findByLocalDateTime();

    //calcular valor do pedido
    List<Pedido> calcularPedido(Pedido pedido);

    //gerar relatorio dos pedidos
    List<Pedido> findAllPedidos();
}
