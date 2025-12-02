package com.example.frota.application.dto.viagem;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record CadastroViagem(

		@NotBlank
		Long id,
		Long caminhaoId,
		Long motoristaId,
		Long solicitacaoId,
		LocalDate dataSaída,
		LocalDate dataEntrada,
		Double kmSaida,
		Double kmChegada,
		Double totalCombustivelLitros
) {}
