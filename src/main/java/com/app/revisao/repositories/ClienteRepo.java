package com.app.revisao.repositories;

import com.app.revisao.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepo extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findByCpf(String cpf);
}
