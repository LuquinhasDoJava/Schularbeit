package com.example.frota.dto;

import com.example.frota.entity.Caixa;
import com.example.frota.entity.Caminhao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CadastroEncomenda(
        Long id,

        @NotBlank(message = "Falta o valor de: caixa")
        Caixa caixa,

        @NotBlank(message = "Falta o valor de: caminhao")
        Caminhao caminhao,

        @Positive(message = "Peso cubado deve ser positiva")
        Double pesoCubado,

        @NotBlank(message = "Falta o valor de: peso") @Positive(message = "Pes deve ser positiva")
        Double peso,

        @NotBlank(message = "Falta o valor de: preco") @Positive(message = "Preco deve ser positiva")
        Double preco
        ) {}
