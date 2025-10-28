package com.example.frota.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public record AtualizacaoCaminhao(
        Long id,
        String modelo,
        Long marcaId,
        String placa,
        @Min(value = 2000, message = "Ano deve ser a partir de 2000")
        Integer ano,
        @Positive(message = "Carga máxima deve ser positiva")
        Double cargaMaxima,
        @Positive(message = "Altura deve ser positiva")
        Double altura,
        @Positive(message = "Comprimento deve ser positiva")
        Double comprimento,
        @Positive(message = "Largura deve ser positiva")
        Double largura
) {}