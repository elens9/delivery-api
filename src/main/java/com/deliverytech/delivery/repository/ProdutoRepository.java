package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    //buscar produtos por ID
    Optional<Produto> findById(Long id);

    //buscar produtos por restaurante
    List<Produto> findByRestauranteContainingIgnoreCase(String restaurante);

    //buscar produtos por categoria
    List<Produto> findByCategoriaContainingIgnoreCase(String categoria);

    //buscar todos os produtos ativos
    List<Produto> findByDisponivelTrue();



}
