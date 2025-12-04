package com.example.frota.caixa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CaixaService {

    @Autowired
    private CaixaRepository caixaRepository;

    @Autowired
    private CaixaMapper caixaMapper;

    public Caixa salvarOuAtualizar(AtualizacaoCaixa dto) {
        if (dto.id() != null) {
            // atualizando: Busca existente e atualiza
            Caixa existente = caixaRepository.findById(dto.id())
                    .orElseThrow(() -> new RuntimeException("Caixa não encontrada com ID: " + dto.id()));
            caixaMapper.updateEntityFromDto(dto, existente);
            return caixaRepository.save(existente);
        } else {
            // criando: Nova caixa
            Caixa novaCaixa = caixaMapper.toEntityFromAtualizacao(dto);
            return caixaRepository.save(novaCaixa);
        }
    }

    public List<Caixa> procurarTodos() {
        return caixaRepository.findAll(Sort.by("id").ascending());
    }

    public void apagarPorId(Long id) {
        caixaRepository.deleteById(id);
    }

    public Optional<Caixa> procurarPorId(Long id) {
        return caixaRepository.findById(id);
    }
}