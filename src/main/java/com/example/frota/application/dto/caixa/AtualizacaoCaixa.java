package com.example.frota.application.dto.caixa;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AtualizacaoCaixa(
        Long id,

		@NotNull(message = "Altura é obrigatória")
	    @Positive(message = "Altura deve ser positiva")
	    Double altura,
	    
	    @NotNull(message = "Largura é obrigatório")
	    @Positive(message = "Largura deve ser positivo")
	    Double largura,
	    
	    @NotNull(message = "Comprimento é obrigatório")
	    @Positive(message = "Comprimento deve ser positivo")
	    Double comprimento,
	    
	    @NotNull(message = "O tipo de material é obrigatório")
	    String material,
	    
	    @NotNull(message = "Limite de peso é obrigatório")
	    @Positive(message = "Limite de peso deve ser positivo")
	    Double limitePeso
	    ) {}
