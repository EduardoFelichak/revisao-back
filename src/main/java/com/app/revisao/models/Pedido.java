package com.app.revisao.models;

import com.app.revisao.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "pedido")
@Data
public class Pedido {
    private  static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "clienteId")
    @JsonBackReference
    private Cliente cliente;
}
