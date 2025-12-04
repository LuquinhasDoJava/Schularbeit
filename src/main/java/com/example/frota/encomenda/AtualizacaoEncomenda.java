package com.example.frota.encomenda;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record AtualizacaoEncomenda(
        Integer id,

        @NotNull(message = "Produto é obrigatório")
        Integer produtoId,

        @NotBlank(message = "Destino é obrigatório")
        String destino,

        @NotNull(message = "Distância é obrigatória")
        @Positive(message = "Distância deve ser positiva")
        BigDecimal distancia,

        @NotNull(message = "Preço é obrigatório")
        @Positive(message = "Preço deve ser positivo")
        BigDecimal preco
) {}