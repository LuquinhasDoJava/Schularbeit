package com.example.frota.api.controller.caixa;

import com.example.frota.domain.caixa.mapper.CaixaMapper;
import com.example.frota.domain.caixa.service.CaixaService;
import com.example.frota.application.dto.caixa.AtualizacaoCaixa;
import com.example.frota.domain.caixa.model.Caixa;

import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/caixa")
@CrossOrigin("*")
public class CaixaController {

    @Autowired
    private CaixaService caixaService;

    @Autowired
    private CaixaMapper caixaMapper;

    @GetMapping
    public ResponseEntity<List<AtualizacaoCaixa>> listarCaixas(){
        List<Caixa> caixas = caixaService.procurarTodos();

        List<AtualizacaoCaixa> dtos = caixas.stream()
                .map(caixaMapper::toAtualizacaoDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtualizacaoCaixa> buscarPorId(@PathVariable Long id){
        return caixaService.procurarPorId(id)
                .map(caixaMapper::toAtualizacaoDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criar(@RequestBody @Valid AtualizacaoCaixa dto) throws Exception {

        try {
            Caixa nova = caixaService.salvarOuAtualizar(dto);
            AtualizacaoCaixa dtoSalvo = caixaMapper.toAtualizacaoDto(nova);

            return ResponseEntity.status(HttpStatus.CREATED).body(nova);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("{\"erro\":\"Caixa não encontrado\"}");
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<AtualizacaoCaixa> atualizar(@RequestBody @Valid AtualizacaoCaixa dto) {
        if (dto.id() == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            caixaService.atualizarCaixa(dto);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            if (caixaService.procurarPorId(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            caixaService.apagarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
