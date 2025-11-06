package com.example.frota.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record AtualizacaoProduto(
        Integer id,

        @NotNull(message = "Caixa é obrigatória")
        Long caixaId,

        @NotNull(message = "Largura é obrigatória")
        @Positive(message = "Largura deve ser positiva")
        BigDecimal largura,

        @NotNull(message = "Altura é obrigatória")
        @Positive(message = "Altura deve ser positiva")
        BigDecimal altura,

        @NotNull(message = "Comprimento é obrigatório")
        @Positive(message = "Comprimento deve ser positivo")
        BigDecimal comprimento,

        @NotNull(message = "Peso é obrigatório")
        @Positive(message = "Peso deve ser positivo")
        BigDecimal peso,

        @NotBlank(message = "Destino é obrigatório")
        String destino
) {}