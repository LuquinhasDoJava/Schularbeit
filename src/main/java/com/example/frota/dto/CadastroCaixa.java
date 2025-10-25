package com.example.frota.dto;

import jakarta.validation.constraints.NotBlank;

public record CadastroCaixa(
        @NotBlank(message = "Nao pode ser cadastro por falta do atributo: id") long id,
        @NotBlank(message = "Nao pode ser cadastro por falta do atributo: altura") int altura,
        @NotBlank(message = "Nao pode ser cadastro por falta do atributo: largura") int largura,
        @NotBlank(message = "Nao pode ser cadastro por falta do atributo: comprimento") int comprimento,
        @NotBlank(message = "Nao pode ser cadastro por falta do atributo: material") String material,
        @NotBlank(message = "Nao pode ser cadastro por falta do atributo: limitePeso") double limitePeso,
        @NotBlank(message = "Nao pode ser cadastro por falta do atributo: volume") double volume
        ) {}
