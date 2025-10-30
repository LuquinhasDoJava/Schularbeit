package com.example.frota.dto;


import jakarta.validation.constraints.Positive;

public record CadastroProduto(
        @Positive double peso,
        @Positive double altura,
        @Positive double largura,
        @Positive double comprimento
) {
}
