package com.deliverytech.delivery.entity;


import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "restaurantes")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 50)
    private String categoria;

    @Column(precision = 10, scale = 2)
    private BigDecimal taxaEntrega;

    private Integer tempoEntregaMinutos;

    @Column(length = 20)
    private String telefone;

    private Boolean ativo = true;
}