package com.example.frota.api.controller.produto;

import com.example.frota.application.dto.produto.AtualizacaoProduto;
import com.example.frota.domain.produto.model.Produto;
import com.example.frota.domain.produto.service.ProdutoService;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produto")
@CrossOrigin("*")
public class ProdutoController {
	
	private final Set<String> CHAVES_VALIDAS = Set.of(
            "cco123",
            "azul123",
            "frota-secret-key"
    );
	
	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping
    public String carregarPaginaListagem(Model model) {
        model.addAttribute("listaTipos", produtoService.procurarTodos());
        return "/produto";
    }

    @GetMapping("/{id}")
    public String mostrarFormulario(@RequestParam(required = false) Long id, Model model) {
		if(id != null) {
			Produto produto = produtoService.procurarPorId(id)
					.orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
			model.addAttribute("produto", produto);
		}
		return "/produto";       
    }
	
	@PostMapping
    @Transactional
    public ResponseEntity<?> criar(
            @RequestHeader("X-API-KEY") String apiKey,
            @RequestBody @Valid AtualizacaoProduto dto) throws Exception {

        if (!CHAVES_VALIDAS.contains(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"erro\":\"Chave API inválida\"}");
        }

        try {
        	produtoService.save(new Produto(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("{\"erro\":\"Solicitação não encontrada\"}");
        }
    }
	
	@PutMapping
    @Transactional
    public ResponseEntity<AtualizacaoProduto> atualizar(@RequestBody @Valid AtualizacaoProduto dto) throws Exception {
        if (dto.id() == null) {
            return ResponseEntity.badRequest().build(); 
        }
        try {
        	produtoService.atualizarProduto(dto);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
	
	@DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            if (produtoService.procurarPorId(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            produtoService.apagarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
