package com.example.frota.dto;

import jakarta.validation.constraints.NotNull;

public record AtualizacaoMarca(
		@NotNull Long id,
		String nome,
		String pais
        ) {}
