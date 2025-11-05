package com.example.frota.caixa;

public record ListagemCaixa(
        Long id,
        int altura,
        int largura,
        int comprimento,
        double pesoMaximo,
        String material,
        double volume
) {
    public ListagemCaixa(Caixa caixa) {
        this(
                caixa.getId(),
                caixa.getAltura(),
                caixa.getLargura(),
                caixa.getComprimento(),
                caixa.getPesoMaximo(),
                caixa.getMaterial(),
                caixa.getVolume()
        );
    }
}