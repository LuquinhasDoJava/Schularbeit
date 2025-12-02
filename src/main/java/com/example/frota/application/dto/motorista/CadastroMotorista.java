package com.example.frota.application.dto.motorista;

import jakarta.validation.constraints.NotBlank;

public record CadastroMotorista(
		@NotBlank
	    Long id,
	    String nome,
	    String cnh,
	    String telefone
		
) {}