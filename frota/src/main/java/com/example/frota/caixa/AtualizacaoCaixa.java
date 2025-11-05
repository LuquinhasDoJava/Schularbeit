package com.example.frota.caixa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AtualizacaoCaixa(
        Long id,

        @NotNull(message = "Altura é obrigatória")
        @Positive(message = "Altura deve ser positiva")
        Integer altura,

        @NotNull(message = "Largura é obrigatória")
        @Positive(message = "Largura deve ser positiva")
        Integer largura,

        @NotNull(message = "Comprimento é obrigatório")
        @Positive(message = "Comprimento deve ser positivo")
        Integer comprimento,

        @NotNull(message = "Peso máximo é obrigatório")
        @Positive(message = "Peso máximo deve ser positivo")
        Double pesoMaximo,

        @NotBlank(message = "Material é obrigatório")
        String material
) {}