package com.example.frota.caminhao;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AtualizacaoCaminhao(
        Long id,
        @NotBlank(message = "Modelo é obrigatório")
        String modelo,

        @NotNull(message = "Ano é obrigatório")
        @Min(value = 2000, message = "Ano deve ser a partir de 2000")
        Integer ano,

        @NotBlank(message = "Placa é obrigatória")
        String placa,

        @Positive(message = "Altura deve ser positiva")
        Double altura,

        @Positive(message = "Largura deve ser positiva")
        Double largura,

        @Positive(message = "Comprimento deve ser positivo")
        Double comprimento,

        @Positive(message = "Carga máxima deve ser positiva")
        Double cargaMaxima,

        @NotNull(message = "Marca é obrigatória")
        Long marcaId
) {}