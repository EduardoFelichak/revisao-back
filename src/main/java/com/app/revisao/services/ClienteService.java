package com.app.revisao.services;

import com.app.revisao.models.Cliente;
import com.app.revisao.models.Pedido;
import com.app.revisao.repositories.ClienteRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClienteService {
    final ClienteRepo repo;

    public ClienteService(ClienteRepo repo) {
        this.repo = repo;
    }

    @Transactional
    public Cliente save(Cliente cliente) {
        for (Pedido pedido : cliente.getPedidos()) {
            pedido.setCliente(cliente);
        }

        return repo.save(cliente);
    }

    @Transactional
    public void delete(UUID id) {
        repo.deleteById(id);
    }

    public List<Cliente> findAll() {
        return repo.findAllWithPedidos();
    }

    public Optional<Cliente> findById(UUID id) {
        return repo.findById(id);
    }

    public Optional<Cliente> findByCpf(String cpf) {
        return repo.findByCpf(cpf);
    }
}
