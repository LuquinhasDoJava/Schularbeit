package com.example.frota.marca;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private MarcaMapper marcaMapper;

    public Marca salvarOuAtualizar(AtualizacaoMarca dto) {
        if (dto.id() != null) {
            // atualizando: Busca existente e atualiza
            Marca existente = marcaRepository.findById(dto.id())
                    .orElseThrow(() -> new RuntimeException("Marca não encontrada com ID: " + dto.id()));
            marcaMapper.updateEntityFromDto(dto, existente);
            return marcaRepository.save(existente);
        } else {
            // criando: Nova marca
            Marca novaMarca = marcaMapper.toEntityFromAtualizacao(dto);
            return marcaRepository.save(novaMarca);
        }
    }

    public List<Marca> procurarTodos() {
        return marcaRepository.findAll(Sort.by("nome").ascending());
    }

    public void apagarPorId(Long id) {
        marcaRepository.deleteById(id);
    }

    public Optional<Marca> procurarPorId(Long id) {
        return marcaRepository.findById(id);
    }
}