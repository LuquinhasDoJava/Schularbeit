package com.example.frota.application.dto.solicitacao;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public record AtualizacaoSolicitacao(
		
		Long id,
	    
		@NotNull(message = "Código do Produto é obrigatório")
		Long produtoId,
		@NotNull(message = "Código da Caixa é obrigatória")
		Long caixaId,
		@NotNull(message = "Código do Caminhão é obrigatório")
		Long caminhaoId,
		@NotNull(message = "Endereço de origem é obrigatório")
		String cepOrigem,
		@NotNull(message = "Endereço de destino é obrigatório")
		String cepDestino,
		LocalDateTime dataSolicitacao,
		
		Double distanciaKm,
		Double custoPedagios,
		Double pesoConsideradoKg,
		Double custoFreteCalculado	
		
	) {

}
