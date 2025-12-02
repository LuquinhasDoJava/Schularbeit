package com.example.frota.application.dto.motorista;

import jakarta.validation.constraints.NotBlank;

public record AtualizacaoMotorista(
	    Long id,
	    
	    @NotBlank(message = "Nome é obrigatório") 
	    String nome,
	    
	    @NotBlank(message = "CNH é obrigatória")
	    String cnh,
	    
	    @NotBlank(message = "Telefone é obrigatório")
	    String telefone
		
) {}

