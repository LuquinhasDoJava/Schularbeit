package com.example.frota.api.controller.solicitacao;

import com.example.frota.application.dto.solicitacao.AtualizacaoSolicitacao;
import com.example.frota.domain.solicitacao.model.Solicitacao;
import com.example.frota.domain.solicitacao.mapper.SolicitacaoMapper;
import com.example.frota.domain.solicitacao.service.SolicitacaoService;

import java.util.List;
import java.util.Set;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/solicitacao")
@CrossOrigin("*")
public class SolicitacaoController {
	
	private final Set<String> CHAVES_VALIDAS = Set.of(
            "cco123",
            "azul123",
            "frota-secret-key"
    );
	
	@Autowired
	private SolicitacaoService solicitacaoService;
	
	@Autowired
    private SolicitacaoMapper solicitacaoMapper;
	
	@GetMapping
    public ResponseEntity<List<AtualizacaoSolicitacao>> listarTodos() {
        List<Solicitacao> solicitacoes = solicitacaoService.procurarTodos();
        List<AtualizacaoSolicitacao> dtos = solicitacoes.stream()
                .map(solicitacaoMapper::toAtualizacaoDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtualizacaoSolicitacao> buscarPorId(@PathVariable Long id) {
        return solicitacaoService.procurarPorId(id)
                .map(solicitacaoMapper::toAtualizacaoDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
	
	@PostMapping
    @Transactional
    public ResponseEntity<?> criar(
            @RequestHeader("X-API-KEY") String apiKey,
            @RequestBody @Valid AtualizacaoSolicitacao dto) throws Exception {

        if (!CHAVES_VALIDAS.contains(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"erro\":\"Chave API inválida\"}");
        }

        try {
            Solicitacao solicitacaoSalva = solicitacaoService.salvarOuAtualizar(dto);
            AtualizacaoSolicitacao dtoSalvo = solicitacaoMapper.toAtualizacaoDto(solicitacaoSalva);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(dtoSalvo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("{\"erro\":\"Solicitação não encontrada\"}");
        }
    }
	
	@PutMapping
    @Transactional
    public ResponseEntity<AtualizacaoSolicitacao> atualizar(@RequestBody @Valid AtualizacaoSolicitacao dto) throws Exception {
        if (dto.id() == null) {
            return ResponseEntity.badRequest().build(); 
        }
        try {
            Solicitacao solicitacaoSalva = solicitacaoService.salvarOuAtualizar(dto);
            AtualizacaoSolicitacao dtoSalvo = solicitacaoMapper.toAtualizacaoDto(solicitacaoSalva);
            return ResponseEntity.ok(dtoSalvo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
	
	@DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            if (solicitacaoService.procurarPorId(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            solicitacaoService.apagarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
}