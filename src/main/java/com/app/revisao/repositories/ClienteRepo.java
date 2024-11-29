package com.app.revisao.repositories;

import com.app.revisao.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepo extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findByCpf(String cpf);

    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.pedidos")
    List<Cliente> findAllWithPedidos();
}
