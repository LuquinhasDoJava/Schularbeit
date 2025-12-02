package com.example.frota.domain.viagem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.frota.application.dto.viagem.AtualizacaoViagem;
import com.example.frota.domain.caminhao.model.Caminhao;
import com.example.frota.domain.motorista.model.Motorista;
import com.example.frota.domain.caminhao.service.CaminhaoService;
import com.example.frota.domain.motorista.service.MotoristaService;
import com.example.frota.domain.solicitacao.model.Solicitacao;
import com.example.frota.domain.solicitacao.service.SolicitacaoService;
import com.example.frota.domain.viagem.mapper.ViagemMapper;
import com.example.frota.domain.viagem.model.Viagem;
import com.example.frota.domain.viagem.repository.ViagemRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ViagemService {
	@Autowired
	private ViagemRepository viagemRepository;
	
	@Autowired
	private CaminhaoService caminhaoService;
	
	@Autowired
	private MotoristaService motoristaService;
	
	@Autowired
	private SolicitacaoService solicitacaoService;
	
	@Autowired
	private ViagemMapper viagemMapper;
	
	public Viagem salvarOuAtualizar(AtualizacaoViagem dto) {
        // Valida se a marca existe
        Caminhao caminhao = caminhaoService.procurarPorId(dto.caminhaoId())
            .orElseThrow(() -> new EntityNotFoundException("Caminhão não encontrado com ID: " + dto.caminhaoId()));
        Motorista motorista = motoristaService.buscar(dto.motoristaId())
                .orElseThrow(() -> new EntityNotFoundException("Caminhão não encontrado com ID: " + dto.motoristaId()));
        Solicitacao solicitacao = solicitacaoService.buscar(dto.solicitacaoId())
                .orElseThrow(() -> new EntityNotFoundException("Caminhão não encontrado com ID: " + dto.solicitacaoId()));
        
        if (dto.id() != null) {
            // atualizando Busca existente e atualiza
            Viagem existente = viagemRepository.findById(dto.id())
                .orElseThrow(() -> new EntityNotFoundException("Viagem não encontrada com ID: " + dto.id()));
            viagemMapper.updateEntityFromDto(dto, existente);
            existente.setCaminhao(caminhao);
            existente.setMotorista(motorista);
            return viagemRepository.save(existente);
        } else {
            // criando Novo caminhão
            Viagem novaViagem = viagemMapper.toEntityFromAtualizacao(dto);
            novaViagem.setCaminhao(caminhao);
            novaViagem.setMotorista(motorista);
            return viagemRepository.save(novaViagem);
        }
    }
	
	public List<Viagem> procurarTodos(){
		return viagemRepository.findAll(Sort.by("data").ascending());
	}
	public void apagarPorId (Long id) {
		viagemRepository.deleteById(id);
	}
	
	public Optional<Viagem> procurarPorId(Long id) {
	    return viagemRepository.findById(id);
	}
	
}