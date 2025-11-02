package com.deliverytech.delivery.service;

import com.deliverytech.delivery.entity.Pedido;
import com.deliverytech.delivery.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    //criar pedido
    public Pedido criarPedido(Pedido pedido){

        //validando pedido
        validarPedido(pedido);

        //definindo pedido como aberto
        pedido.setAberto(true);

        //definindo data do pedido
        pedido.setDataPedido(LocalDateTime.now());

        return pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    //buscar pedido por id
    public Optional<Pedido> buscarPorId(Long id){
        return pedidoRepository.findById(id);
    }

    //buscar pedido por status aberto
    public List<Pedido> buscarPedidosAbertos(){
        return pedidoRepository.findByAberto();
    }

    //buscar pedido por status finalizado
    public List<Pedido> buscarPedidosFinalizados(){
        return pedidoRepository.findByFinalizado();
    }

    //buscar pedidos por data
    public List<Pedido> buscarPedidosPorData(LocalDateTime data){
        return pedidoRepository.findByLocalDateTime();
    }



    //regras de negocio
    private void validarPedido(Pedido pedido){

        if(pedido.getPedido() == null || pedido.getPedido().trim().isEmpty()){
            throw new IllegalArgumentException("Conteúdo do pedido é obrigatório");
        }
        if(pedido.getCliente() == null || pedido.getCliente().trim().isEmpty()){
            throw new IllegalArgumentException("Cliente é obrigatório");
        }
        if(pedido.getValor() == null || pedido.getValor() <= 0){
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }
        if(pedido.getPedido().length() < 2){
            throw new IllegalArgumentException("Conteúdo do pedido deve ter mais de 2 caracteres");
        }
    }
}
