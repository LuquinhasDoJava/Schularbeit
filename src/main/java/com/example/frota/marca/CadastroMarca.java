package com.example.frota.marca;

import jakarta.validation.constraints.NotBlank;

public record CadastroMarca(
        @NotBlank String nome,
        @NotBlank String pais
) {
}