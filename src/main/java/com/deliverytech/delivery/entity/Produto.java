package com.deliverytech.delivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produtos")

public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String produto;
    private String categoria;
    private float valor;
    private int disponibilidade;
    private String restaurante;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(nullable = true)
    private Boolean ativo;

    public void inativar(){
        this.ativo = false;
    }



}
