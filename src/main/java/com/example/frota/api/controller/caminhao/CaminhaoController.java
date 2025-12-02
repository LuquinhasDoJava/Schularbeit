package com.example.frota.api.controller.caminhao;

import com.example.frota.application.dto.caminhao.AtualizacaoCaminhao;
import com.example.frota.domain.caminhao.mapper.CaminhaoMapper;
import com.example.frota.domain.caminhao.model.Caminhao;
import com.example.frota.domain.caminhao.service.CaminhaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/caminhao")
@CrossOrigin("*")
public class CaminhaoController {

    @Autowired
    private CaminhaoService caminhaoService;

    @Autowired
    private CaminhaoMapper caminhaoMapper;

    @GetMapping
    public ResponseEntity<List<AtualizacaoCaminhao>> listarTodos() {
        List<Caminhao> caminhoes = caminhaoService.procurarTodos();
        List<AtualizacaoCaminhao> dtos = caminhoes.stream()
                .map(caminhaoMapper::toAtualizacaoDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtualizacaoCaminhao> buscarPorId(@PathVariable Long id) {
        return caminhaoService.procurarPorId(id)
                .map(caminhaoMapper::toAtualizacaoDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criar(@RequestBody @Valid AtualizacaoCaminhao dto) {

        try {
            Caminhao caminhaoSalvo = caminhaoService.salvarOuAtualizar(dto);
            AtualizacaoCaminhao dtoSalvo = caminhaoMapper.toAtualizacaoDto(caminhaoSalvo);

            return ResponseEntity.status(HttpStatus.CREATED).body(dtoSalvo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("{\"erro\":\"Marca não encontrada\"}");
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<AtualizacaoCaminhao> atualizar(@RequestBody @Valid AtualizacaoCaminhao dto) {
        if (dto.id() == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Caminhao caminhaoSalvo = caminhaoService.salvarOuAtualizar(dto);
            AtualizacaoCaminhao dtoSalvo = caminhaoMapper.toAtualizacaoDto(caminhaoSalvo);
            return ResponseEntity.ok(dtoSalvo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            if (caminhaoService.procurarPorId(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            caminhaoService.apagarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}