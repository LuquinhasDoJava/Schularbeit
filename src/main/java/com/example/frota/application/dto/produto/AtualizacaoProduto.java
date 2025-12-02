package com.example.frota.application.dto.produto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AtualizacaoProduto(
		Long id,
		
		@NotNull(message = "Nome é obrigatório")
		String nome,
		
		@NotNull(message = "Peso é obrigatória")
	    @Positive(message = "Peso deve ser positivo")
	    Double peso,
	    
	    @NotNull(message = "Preço é obrigatório")
	    @Positive(message = "Preço deve ser positivo")
	    Double preco
	    
		) {}
