package com.example.frota.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.frota.dto.AtualizacaoEncomenda;
import com.example.frota.entity.Caixa;
import com.example.frota.entity.Encomenda;
import com.example.frota.entity.Produto;
import com.example.frota.mapper.EncomendaMapper;
import com.example.frota.repository.EncomendaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EncomendaService {
	
	@Autowired
	private EncomendaRepository encomendaRepository;
	
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private CaixaService caixaService;
	
	@Autowired
	private EncomendaMapper encomendaMapper;
	
	public Encomenda salvarOuAtualizar(AtualizacaoEncomenda dto) {
        Produto produto = produtoService.procurarPorId(dto.produtoId())
            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + dto.produtoId()));
        Caixa caixa = caixaService.procurarPorId(dto.caixaId())
                .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada com ID: " + dto.caixaId()));
        if (dto.id() != null) {
        	Encomenda existente = encomendaRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada com ID: " + dto.id()));
        	encomendaMapper.updateEntityFromDto(dto, existente);
            existente.setProduto(produto);
            existente.setCaixa(caixa);
            return encomendaRepository.save(existente);
        } else {
        	Encomenda novaEncomenda = encomendaMapper.toEntityFromAtualizacao(dto);
        	novaEncomenda.setProduto(produto);
        	novaEncomenda.setCaixa(caixa);
            
            return encomendaRepository.save(novaEncomenda);
        }
    }
	
	public List<Encomenda> procurarTodos(){
		return encomendaRepository.findAll();
	}
	public void apagarPorId (Long id) {
		encomendaRepository.deleteById(id);
	}
	
	public Optional<Encomenda> procurarPorId(Long id) {
		return encomendaRepository.findById(id);
	}
	    
}
