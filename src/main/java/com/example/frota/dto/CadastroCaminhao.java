package com.example.frota.dto;

import com.example.frota.entity.Marca;
import jakarta.validation.constraints.NotBlank;

public record CadastroCaminhao(
        @NotBlank Long id,
        @NotBlank String modelo,
        @NotBlank Marca marca,
        @NotBlank String placa,
        @NotBlank double cargaMaxima,
        @NotBlank int ano,
        @NotBlank double altura,
        @NotBlank double comprimento,
        @NotBlank double largura,
        double volume
        ) {}

