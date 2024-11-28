package com.app.revisao.controllers;

import com.app.revisao.dtos.ClienteDto;
import com.app.revisao.models.Cliente;
import com.app.revisao.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("clientes")
public class ClienteController {
    final private ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid ClienteDto dto, BindingResult result){
        if (result.hasErrors()){
            List<String> msgErros = result.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(msgErros);
        }

        var cliente = new Cliente();
        BeanUtils.copyProperties(dto, cliente);

        if (!service.findByCpf(cliente.getCpf()).isEmpty())
            return ResponseEntity
                    .badRequest().body("Já existe um cliente com este CPF");

        return ResponseEntity.ok().body(service.save(cliente));
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAll(){
        return ResponseEntity.ok().body(
                service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Cliente>> getById(@PathVariable(value = "id") UUID id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody @Valid Cliente cliente) {

        Optional<Cliente> clienteOptional = service.findById(cliente.getId());

        if (clienteOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }

        Cliente clienteExistente = clienteOptional.get();

        BeanUtils.copyProperties(cliente, clienteExistente, "id", "pedidos");

        Cliente clienteAtualizado = service.save(clienteExistente);

        return ResponseEntity.ok(clienteAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
        Optional<Cliente> clienteOptional = service.findById(id);

        if(clienteOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Cliente não encontrado"
            );
        }

        service.delete(id);

        return ResponseEntity.status(HttpStatus.OK).body(
                "Cliente apagado com sucesso"
        );
    }
}
