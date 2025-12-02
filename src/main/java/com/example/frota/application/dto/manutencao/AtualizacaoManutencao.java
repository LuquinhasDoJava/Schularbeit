package com.example.frota.application.dto.manutencao;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record AtualizacaoManutencao(
		Long id,
		
		@NotBlank(message = "Caminhão é obrigatório") 
		Long caminhaoId,
		
		@NotBlank(message = "Data é obrigatória") 
		LocalDate data,
		
		@Positive(message = "KM deve ser positivo")
		Double kmNoMomento,
		
		@NotBlank(message = "Custo é obrigatório") 
		@Positive(message = "Custo deve ser positivo")
		Double custo,
		
		String descricao
		
) {}
