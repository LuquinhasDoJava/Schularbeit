package com.example.frota.dto;

import jakarta.validation.constraints.NotBlank;

public record CadastroMarca(
		@NotBlank(message = "Nao pode ser cadastro por falta do atributo: nome") String nome,
		@NotBlank(message = "Nao pode ser cadastro por falta do atributo: paise") String pais
        ) {}
