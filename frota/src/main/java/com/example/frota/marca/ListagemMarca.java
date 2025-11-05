package com.example.frota.marca;

public record ListagemMarca(
        Long id,
        String nome,
        String pais
) {
    public ListagemMarca(Marca marca) {
        this(marca.getId(), marca.getNome(), marca.getPais());
    }
}