package com.deliverytech.delivery.repository;

import com.deliverytech.delivery.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface  ClienteRepository extends JpaRepository<Cliente, Long> {

    //buscar cliente por email
    Optional<Cliente> findByEmail(String email);

    //verificar se email se já existe
    boolean existsByEmail(String email);

    //buscar clientes ativos
    List<Cliente> findByAtivoTrue();

    //buscar clientes por nome
    List<Cliente> findByNomeContainingIgnoreCase(String nome);


}
