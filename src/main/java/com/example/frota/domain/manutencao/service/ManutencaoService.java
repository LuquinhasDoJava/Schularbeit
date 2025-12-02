package com.example.frota.domain.manutencao.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.frota.domain.manutencao.mapper.ManutencaoMapper;
import com.example.frota.domain.manutencao.model.Manutencao;
import com.example.frota.domain.manutencao.repository.ManutencaoRepository;

import jakarta.persistence.EntityNotFoundException;

import com.example.frota.application.dto.manutencao.AtualizacaoManutencao;
import com.example.frota.domain.caminhao.model.Caminhao;
import com.example.frota.domain.caminhao.service.CaminhaoService;

@Service
public class ManutencaoService {
	@Autowired
	private ManutencaoRepository manutencaoRepository;
	
	@Autowired
	private CaminhaoService caminhaoService;
	
	@Autowired
	private ManutencaoMapper manutencaoMapper;
	
	public Manutencao salvarOuAtualizar(AtualizacaoManutencao dto) {
        // Valida se a marca existe
        Caminhao caminhao = caminhaoService.procurarPorId(dto.caminhaoId())
            .orElseThrow(() -> new EntityNotFoundException("Caminhão não encontrado com ID: " + dto.caminhaoId()));
        if (dto.id() != null) {
            // atualizando Busca existente e atualiza
            Manutencao existente = manutencaoRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Manutencão não encontrado com ID: " + dto.id()));
            manutencaoMapper.updateEntityFromDto(dto, existente);
            existente.setCaminhao(caminhao);
            return manutencaoRepository.save(existente);
        } else {
            // criando Novo caminhão
            Manutencao novoManutencao = manutencaoMapper.toEntityFromAtualizacao(dto);
            novoManutencao.setCaminhao(caminhao);
            
            return manutencaoRepository.save(novoManutencao);
        }
    }
	
	public List<Manutencao> procurarTodos(){
		return manutencaoRepository.findAll(Sort.by("data").ascending());
	}
	public void apagarPorId (Long id) {
		manutencaoRepository.deleteById(id);
	}
	
	public Optional<Manutencao> procurarPorId(Long id) {
	    return manutencaoRepository.findById(id);
	}
	
}