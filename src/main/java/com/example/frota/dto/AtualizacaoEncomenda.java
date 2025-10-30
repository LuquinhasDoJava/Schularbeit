package com.example.frota.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AtualizacaoEncomenda(
        @NotNull Long id,
        Long caixaId,
        Long caminhaoId,
        Long produtoId,
        @Positive Double pesoReal,
        @Positive Double distanciaKm
        ) {}
