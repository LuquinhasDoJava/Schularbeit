package com.example.frota.domain.caixa.service;

import java.util.List;
import java.util.Optional;

import com.example.frota.application.dto.caixa.AtualizacaoCaixa;
import com.example.frota.domain.caixa.model.Caixa;
import com.example.frota.domain.caixa.repository.CaixaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CaixaService {
	@Autowired
	private CaixaRepository caixaRepository;

	public Caixa salvarOuAtualizar(AtualizacaoCaixa dto) {
        if(dto.id() != null){
            Caixa existente = caixaRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada"));
            existente.atualizarInformacoes(dto);
            return caixaRepository.save(existente);
        } else {
            Caixa novaCaixa = new Caixa(dto);

            return caixaRepository.save(novaCaixa);
        }
	}

	public List<Caixa> procurarTodos() {
		return caixaRepository.findAll(Sort.by("material").ascending());
	}

	public void apagarPorId(Long id) {
		caixaRepository.deleteById(id);
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
