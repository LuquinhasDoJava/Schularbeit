package com.example.frota.dto;

import jakarta.validation.constraints.NotBlank;

public record CadastroMarca(
        @NotBlank Long id,
        @NotBlank String nome,
        @NotBlank String pais
) {}
