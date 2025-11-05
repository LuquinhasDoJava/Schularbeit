package com.example.frota.caminhao;

import jakarta.validation.constraints.NotBlank;

public record CadastroCaminhao(
		@NotBlank String modelo,
        int ano,
		String placa,
        double altura,
        double largura,
        double comprimento,
		double cargaMaxima
        ) {

}

