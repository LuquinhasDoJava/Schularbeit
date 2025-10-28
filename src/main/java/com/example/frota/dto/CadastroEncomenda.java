package com.example.frota.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CadastroEncomenda(
        @NotNull Long caixaId,
        @NotNull Long caminhaoId,
        @NotNull Long encomendaId,
        @Positive double pesoReal,
        @Positive double distanciaKm
        ) {}
