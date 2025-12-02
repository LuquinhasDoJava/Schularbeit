package com.example.frota.domain.motorista.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.frota.application.dto.motorista.AtualizacaoMotorista;
import com.example.frota.domain.motorista.model.Motorista;
import com.example.frota.domain.motorista.repository.MotoristaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MotoristaService {
	@Autowired
	private MotoristaRepository motoristaRepository;

	public Motorista save(Motorista motorista) {
		return motoristaRepository.save(motorista);
	} 

	public List<Motorista> procurarTodos() {
		return motoristaRepository.findAll(Sort.by("material").ascending());
	}

	public void apagarPorId(Long id) {
		motoristaRepository.deleteById(id);
	}
	
	public Optional<Motorista> buscar(long id) {
		return motoristaRepository.findById(id);
	}

	public Optional<Motorista> procurarPorId(Long id) {
		return motoristaRepository.findById(id);
	}
	
	public void atualizarMotorista(AtualizacaoMotorista dados) {
	    Motorista motorista = motoristaRepository.findById(dados.id())
	        .orElseThrow(() -> new EntityNotFoundException("Motorista não encontrada"));
	    motorista.atualizarInformacoes(dados);
	}

}