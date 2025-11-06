package com.example.frota.produto;


public record ListagemProduto(
        Integer id,
        Long caixaId,
        double largura,
        double altura,
        double comprimento,
        double peso,
        double volume
) {
    public ListagemProduto(Produto produto) {
        this(
                produto.getId(),
                produto.getCaixa() != null ? produto.getCaixa().getId() : null,
                produto.getLargura(),
                produto.getAltura(),
                produto.getComprimento(),
                produto.getPeso(),
                produto.getVolume()
        );
    }
}