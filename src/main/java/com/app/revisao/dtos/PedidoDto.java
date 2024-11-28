package com.app.revisao.dtos;

import com.app.revisao.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Currency;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PedidoDto {
    private UUID id;

    @NotBlank(message = "Descrição precisa ser informada")
    private String descricao;

    @NotNull(message = "O valor não pode ser nulo")
    @Positive(message = "Valor tem que ser positivo")
    private BigDecimal valor;

    @NotNull(message = "Status precisa ser informado")
    private Status status;

    @NotNull(message = "O Id do cliente precisa ser informado")
    private UUID clienteId;
}
