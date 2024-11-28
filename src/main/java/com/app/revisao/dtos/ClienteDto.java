package com.app.revisao.dtos;

import com.app.revisao.models.Pedido;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ClienteDto {
    private UUID id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 50)
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    private String cpf;

    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    private String endereco;

    private List<Pedido> pedidos;
}
