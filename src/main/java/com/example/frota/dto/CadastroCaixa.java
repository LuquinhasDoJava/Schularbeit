package com.example.frota.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CadastroCaixa(
        @Positive double altura,
        @Positive double largura,
        @Positive double comprimento,
        @NotBlank String material,
        @Positive double limitePeso
) {}