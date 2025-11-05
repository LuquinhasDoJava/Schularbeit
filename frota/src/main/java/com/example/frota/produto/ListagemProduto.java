package com.example.frota.produto;

import java.math.BigDecimal;

public record ListagemProduto(
        Integer id,
        Long caixaId,
        BigDecimal largura,
        BigDecimal altura,
        BigDecimal comprimento,
        BigDecimal peso,
        BigDecimal volume
) {
    public ListagemProduto(Produto produto) {
        this(
                produto.getId(),
                produto.getCaixa() != null ? produto.getCaixa().getId() : null,
                produto.getLargura(),
                produto.getAltura(),
                produto.getComprimento(),
                produto.getPeso(),
                produto.getVolume() != null ? produto.getVolume() : BigDecimal.ZERO
        );
    }
}