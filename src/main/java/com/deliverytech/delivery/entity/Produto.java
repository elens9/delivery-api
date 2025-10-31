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
    private Float valor;
    private String restaurante;

    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @Column(nullable = true)
    private Boolean disponivel;

    public void inativar(){
        this.disponivel = false;
    }



}
