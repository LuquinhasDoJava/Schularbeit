package com.example.frota.dto;

import com.example.frota.entity.Marca;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AtualizacaoCaminhao(
        String modelo,
        Marca marca,
        String placa,
        @Min(value = 2000, message = "Ano deve ser a partir de 2000")
        Integer ano,
        @Positive(message = "Carga moxima deve ser positiva")
        Double cargaMaxima,
        @Positive(message = "Altura deve ser positiva")
        Double altura,
        @Positive(message = "Comprimento deve ser positiva")
        Double comprimento,
        @Positive(message = "Largura deve ser positiva")
        Double largura
	) {}


