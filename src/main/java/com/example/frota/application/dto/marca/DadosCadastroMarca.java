package com.example.frota.application.dto.marca;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroMarca(	
		@NotBlank
		String nome,
		String pais) {
}
