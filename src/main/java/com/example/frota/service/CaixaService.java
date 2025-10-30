package com.example.frota.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.frota.entity.Caixa;
import com.example.frota.entity.Produto;
import com.example.frota.repository.CaixaRepository;

@Service
public class CaixaService {
	
    @Autowired
	private CaixaRepository caixaRepository;


    public Caixa salvar(Caixa caixa) {
		return caixaRepository.save(caixa);
	}
	public void apagarPorId (Long id) {
		caixaRepository.deleteById(id);
	}
    public Caixa escolherCaixaMaisProxima(Produto produto) {
    	return caixaRepository.findCaixaMaisProxima(produto.getComprimento(), produto.getLargura(), produto.getAltura(), produto.getPeso());
    }
    public Optional<Caixa> procurarPorId(Long id) {
		return caixaRepository.findById(id);
	}
    
}
