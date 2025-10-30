package com.example.frota.service;

import java.util.List;
import java.util.Optional;

import com.example.frota.entity.Marca;
import com.example.frota.dto.AtualizacaoMarca;
import com.example.frota.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class MarcaService {
	@Autowired
	private MarcaRepository marcaRepository;
	
	public Marca salvar(Marca marca) {
		return marcaRepository.save(marca);
	}
	public List<Marca> procurarTodos(){
		return marcaRepository.findAll(Sort.by("nome").ascending());
	}
	public void apagarPorId (Long id) {
		marcaRepository.deleteById(id);
	}
	public Optional<Marca> procurarPorId( Long id) {
		return marcaRepository.findById(id);
	}
	public void atualizarMarca(AtualizacaoMarca dados) {
	    Marca marca = marcaRepository.findById(dados.id())
	        .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));
	    marca.atualizarInformacoes(dados);
	}
	
}
