package com.example.frota.caixa;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.frota.produto.Produto;

import jakarta.persistence.EntityNotFoundException;



@Service
public class CaixaService {
	
    @Autowired
	private CaixaRepository caixaRepository;


    public Caixa salvar(Caixa caixa) {
		return caixaRepository.save(caixa);
	}
    public List<Caixa> procurarTodos(){
		return caixaRepository.findAll(Sort.by("id").ascending());
	}
	public void apagarPorId (Long id) {
		caixaRepository.deleteById(id);
	}
	//lista filtrada para a encomenda
    public List<Caixa> listaCaixasPossiveis(Produto produto) {
    	return caixaRepository.findCaixasPossiveis(produto.getComprimento(), produto.getLargura(), produto.getAltura(), produto.getPeso());
    }
    public Optional<Caixa> procurarPorId(Long id) {
		return caixaRepository.findById(id);
	}
    public void atualizarCaixa(AtualizacaoCaixa dados) {
	    Caixa caixa = caixaRepository.findById(dados.id())
	        .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada"));
	    caixa.atualizarInformacoes(dados);
	}
    
}