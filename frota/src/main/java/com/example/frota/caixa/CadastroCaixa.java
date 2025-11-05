package com.example.frota.caixa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CadastroCaixa(
        @NotNull @Positive int altura,
        @NotNull @Positive int largura,
        @NotNull @Positive int comprimento,
        @NotNull @Positive double pesoMaximo,
        @NotBlank String material
) {
}