package com.example.frota.dto;

import jakarta.validation.constraints.NotNull;

public record AtualizacaoCaixa(
        @NotNull Long id,
        double largura,
        double comprimento,
        double altura
){}
