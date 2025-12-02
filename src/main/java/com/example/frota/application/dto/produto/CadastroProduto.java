package com.example.frota.application.dto.produto;

import jakarta.validation.constraints.NotBlank;

public record CadastroProduto(
		@NotBlank
		String nome,
		Double peso,
		Double preco
		) {}
