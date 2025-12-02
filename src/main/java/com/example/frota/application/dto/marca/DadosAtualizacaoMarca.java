package com.example.frota.application.dto.marca;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoMarca(
		@NotNull
		Long id,
		String nome,
		String pais) {
}