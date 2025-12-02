package com.example.frota.application.dto.manutencao;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public record CadastroManutencao(
		@NotBlank
		Long id,
		Long caminhaoId,
		LocalDate data,
		Double kmNoMomento,
		Double custo,
		String descricao
		
) {}