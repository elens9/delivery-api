package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    //buscar produtos por nome
    Optional<Produto> findByNomeContainingIgnoreCase(String nome);


}
