package com.example.frota.application.dto.caminhao;

import com.example.frota.domain.marca.model.Marca;

import jakarta.validation.constraints.NotBlank;

public record CadastroCaminhao(
		@NotBlank
		String modelo,
		String placa,
		Marca marca,
		double cargaMaxima,
		int ano,
		double comprimento,
		double largura,
		double altura,
		double metragemCubica) {

}

