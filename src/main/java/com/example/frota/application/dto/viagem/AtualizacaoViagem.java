package com.example.frota.application.dto.viagem;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record AtualizacaoViagem(
		
		Long id,
		
		@NotBlank(message = "Caminhão é obrigatório") 
		Long caminhaoId,
		
		@NotBlank(message = "Motorista é obrigatório") 
		Long motoristaId,
		
		@NotBlank(message = "Solicitação é obrigatório") 
		Long solicitacaoId,
		
		LocalDate dataSaída,
		
		LocalDate dataEntrada,
		
		@Positive(message = "KM Saída deve ser positivo")
		Double kmSaida,
		
		@Positive(message = "KM Chegada deve ser positivo")
		Double kmChegada,
		
		@Positive(message = "Total de Combustível deve ser positivo")
		Double totalCombustivelLitros
		
) {}

