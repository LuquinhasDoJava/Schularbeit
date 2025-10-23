package com.example.frota.caixa;

public record CadastroCaixa(
        long id,
        int altura,
        int largura,
        int comprimento,
        String material,
        double limitePeso
) {
}
