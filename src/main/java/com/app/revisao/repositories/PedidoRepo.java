package com.app.revisao.repositories;

import com.app.revisao.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PedidoRepo extends JpaRepository<Pedido, UUID> { }
