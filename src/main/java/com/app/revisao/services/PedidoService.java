package com.app.revisao.services;

import com.app.revisao.models.Pedido;
import com.app.revisao.repositories.PedidoRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoService {
    final PedidoRepo repo;

    public PedidoService(PedidoRepo repo) {
        this.repo = repo;
    }

    @Transactional
    public Pedido save(Pedido pedido) {
        return repo.save(pedido);
    }

    @Transactional
    public void delete(UUID id) {
        repo.deleteById(id);
    }

    public List<Pedido> findAll() {
        return repo.findAll();
    }

    public Optional<Pedido> findById(UUID id) {
        return repo.findById(id);
    }
}
