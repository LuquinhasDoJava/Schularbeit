package com.example.frota.encomenda;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalTime;

public record CadastroEncomenda(
        @NotNull Integer produtoId,
        @NotBlank String origem,
        @NotBlank String destino,
        @NotNull @Positive BigDecimal distancia,
        @NotNull @Positive BigDecimal preco,
        @NotNull LocalTime horarioColeta
) {}