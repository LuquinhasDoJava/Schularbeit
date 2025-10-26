package com.example.frota.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CadastroCaminhao(
        @NotBlank String modelo,
        @NotNull Long marcaId,
        @NotBlank String placa,
        @Positive double cargaMaxima,
        @Positive int ano,
        @Positive double altura,
        @Positive double comprimento,
        @Positive double largura
) {}