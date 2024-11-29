package com.app.revisao.controllers;

import com.app.revisao.dtos.PedidoDto;
import com.app.revisao.models.Cliente;
import com.app.revisao.models.Pedido;
import com.app.revisao.repositories.ClienteRepo;
import com.app.revisao.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/pedidos")
public class PedidoController {
    final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @Autowired
    private ClienteRepo clienteRepo;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid PedidoDto dto, BindingResult result) {
        if (result.hasErrors()){
            List<String> msgErros = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(msgErros);
        }

        if(dto.getClienteId() == null){
            return ResponseEntity
                    .badRequest().body("O pedido deve estar associado à uma cliente");
        }
        Optional<Cliente> cliente = clienteRepo.findById(dto.getClienteId());

        if(!cliente.isPresent()){
            return  ResponseEntity.badRequest().
                    body("Cliente não encontrado com Id fornecido");
        }

        Pedido pedido = new Pedido();
        BeanUtils.copyProperties(dto, pedido);

        pedido.setCliente(cliente.get());
        return ResponseEntity.ok().body(service.save(pedido));
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> getAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> getById(@PathVariable(value = "id") UUID id) {
        Optional<Pedido> pedido = service.findById(id);

        return pedido.map(model -> ResponseEntity.ok().body(model)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody PedidoDto dto) {
        Optional<Pedido> pedidoOptional = service.findById(dto.getId());

        if (!pedidoOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pedido não encontrado");
        }

        Pedido pedido = pedidoOptional.get();

        Optional<Cliente> clienteOptional = clienteRepo.findById(dto.getClienteId());
        if (!clienteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }

        pedido.setDescricao(dto.getDescricao());
        pedido.setValor(dto.getValor());
        pedido.setStatus(dto.getStatus());
        pedido.setCliente(clienteOptional.get());

        return ResponseEntity.status(HttpStatus.OK).body(service.save(pedido));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
        Optional<Pedido> pedidoOptional = service.findById(id);

        if(pedidoOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Pedido não encontrado"
            );
        }

        service.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                "Pedido apagado com sucesso"
        );
    }
}
