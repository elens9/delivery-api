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
@Table(name = "restaurantes")

public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String restaurante;
    private String categoria;
    private Double avaliacao;

    //criando data_cadastro no banco
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    //criando coluna de ativo no banco
    @Column(nullable = true)
    private Boolean ativo;

    public void inativar(){
        this.ativo = false;
    }



}
