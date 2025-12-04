package com.example.frota.encomenda;

import java.math.BigDecimal;

public record ListagemEncomenda(
        Integer id,
        Integer produtoId,
        String destino,
        BigDecimal distancia,
        BigDecimal preco
) {
    public ListagemEncomenda(Encomenda encomenda) {
        this(
                encomenda.getId(),
                encomenda.getProduto() != null ? encomenda.getProduto().getId() : null,
                encomenda.getDestino(),
                encomenda.getDistancia(),
                encomenda.getPreco()
        );
    }
}