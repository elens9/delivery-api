package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    //buscar por nome, categoria, ativos, ordencao de avaliacao

    //buscar restaurantes por id
    Optional<Restaurante> findById(Long id);

    //buscar restaurantes por nome
    List<Restaurante> findByRestauranteContainingIgnoreCase(String restaurante);

    //buscar restaurantes por categoria
    List<Restaurante> findByCategoriaContainingIgnoreCase(String categoria);

    //listar restaurantes por avaliacao descrescente
    List<Restaurante> findByAvaliacaoGreaterThanEqualsOrderByAvaliacaoDesc(Double avaliacao);

    //buscar rastaurantes ativos
    List<Restaurante> findByAtivoTrue();


}
