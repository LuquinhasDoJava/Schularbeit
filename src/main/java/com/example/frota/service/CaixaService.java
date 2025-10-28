package com.example.frota.service;

import com.example.frota.entity.Caixa;
import com.example.frota.repository.CaixaRepository;

import java.util.List;

public class CaixaService {

    private CaixaRepository repository;

    public List<Caixa> procurarPorCaixa(double altura, double largura, double comprimento){
        return repository.findCaixaByAlturaLarguraComprimento(altura, largura, comprimento);
    }

}
