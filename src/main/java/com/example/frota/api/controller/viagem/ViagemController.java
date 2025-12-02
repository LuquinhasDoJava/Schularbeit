package com.example.frota.api.controller.viagem;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.frota.api.annotations.PublicRoute;
import com.example.frota.application.dto.viagem.AtualizacaoViagem;
import com.example.frota.domain.viagem.mapper.ViagemMapper;
import com.example.frota.domain.viagem.model.Viagem;
import com.example.frota.domain.viagem.service.ViagemService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/viagem")
@CrossOrigin("*")
public class ViagemController {
	
	private final Set<String> CHAVES_VALIDAS = Set.of(
            "cco123",
            "azul123",
            "frota-secret-key"
    );
	
	@Autowired
	private ViagemService viagemService;
	
	@Autowired
	private ViagemMapper viagemMapper;
	
	@GetMapping
    public ResponseEntity<List<AtualizacaoViagem>> listarTodos() {
        List<Viagem> viagens = viagemService.procurarTodos();
        List<AtualizacaoViagem> dtos = viagens.stream()
                .map(viagemMapper::toAtualizacaoDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtualizacaoViagem> buscarPorId(@PathVariable Long id) {
        return viagemService.procurarPorId(id)
                .map(viagemMapper::toAtualizacaoDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> criar(
            @RequestHeader("X-API-KEY") String apiKey,
            @RequestBody @Valid AtualizacaoViagem dto) {

        if (!CHAVES_VALIDAS.contains(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"erro\":\"Chave API inválida\"}");
        }

        try {
            Viagem viagemSalva = viagemService.salvarOuAtualizar(dto);
            AtualizacaoViagem dtoSalvo = viagemMapper.toAtualizacaoDto(viagemSalva);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(dtoSalvo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("{\"erro\":\"Caminhão não encontrado\"}");
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<AtualizacaoViagem> atualizar(@RequestBody @Valid AtualizacaoViagem dto) {
        if (dto.id() == null) {
            return ResponseEntity.badRequest().build(); 
        }
        try {
            Viagem viagemSalva = viagemService.salvarOuAtualizar(dto);
            AtualizacaoViagem dtoSalvo = viagemMapper.toAtualizacaoDto(viagemSalva);
            return ResponseEntity.ok(dtoSalvo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            if (viagemService.procurarPorId(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            viagemService.apagarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
