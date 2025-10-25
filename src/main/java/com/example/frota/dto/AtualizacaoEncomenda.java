package com.example.frota.dto;

import com.example.frota.entity.Caixa;
import com.example.frota.entity.Caminhao;
import jakarta.validation.constraints.Positive;

public record AtualizacaoEncomenda(
        Caixa caixa,

        Caminhao caminhao,

        @Positive(message = "Peso cubado deve ser positiva")
        Double pesoCubado,

        @Positive(message = "Pes deve ser positiva")
        Double peso,

        @Positive(message = "Preco deve ser positiva")
        Double preco
        ) {}
