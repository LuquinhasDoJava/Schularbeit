package com.example.frota.marca;

import jakarta.validation.constraints.NotBlank;

public record AtualizacaoMarca(
        Long id,

        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @NotBlank(message = "País é obrigatório")
        String pais
) {}