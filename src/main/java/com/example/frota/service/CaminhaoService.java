package com.example.frota.service;

import java.util.List;
import java.util.Optional;

import com.example.frota.dto.AtualizacaoCaminhao;
import com.example.frota.mapper.CaminhaoMapper;
import com.example.frota.repository.CaminhaoRepository;
import com.example.frota.entity.Caminhao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

import com.example.frota.entity.Marca;

@Service
public class CaminhaoService {
    @Autowired
    private CaminhaoRepository caminhaoRepository;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private CaminhaoMapper caminhaoMapper;

    public Caminhao salvarOuAtualizar(AtualizacaoCaminhao dto) {
        // Valida se a marca existe
        Marca marca = marcaService.procurarPorId(dto.marcaId())
                .orElseThrow(() -> new EntityNotFoundException("Marca não encontrada com ID: " + dto.marcaId()));
        if (dto.id() != null) {
            // atualizando Busca existente e atualiza
            Caminhao existente = caminhaoRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Caminhão não encontrado com ID: " + dto.id()));
            caminhaoMapper.updateEntityFromDto(dto, existente);
            existente.setMarca(marca); // Atualiza a marca
            return caminhaoRepository.save(existente);
        } else {
            // criando Novo caminhão
            Caminhao novoCaminhao = caminhaoMapper.toEntityFromAtualizacao(dto);
            novoCaminhao.setMarca(marca); // Define a marca completa

            return caminhaoRepository.save(novoCaminhao);
        }
    }

    public List<Caminhao> procurarTodos(){
        return caminhaoRepository.findAll(Sort.by("modelo").ascending());
    }
    public void apagarPorId (Long id) {
        caminhaoRepository.deleteById(id);
    }

    public Optional<Caminhao> procurarPorId(Long id) {
        return caminhaoRepository.findById(id);
    }
}