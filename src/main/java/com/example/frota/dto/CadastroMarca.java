package com.example.frota.dto;

import jakarta.validation.constraints.NotBlank;

public record CadastroMarca(
        @NotBlank String nome,
        @NotBlank String pais
) {}
