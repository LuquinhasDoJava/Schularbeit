package com.example.frota.api.controller.motorista;

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

import com.example.frota.application.dto.motorista.AtualizacaoMotorista;
import com.example.frota.domain.motorista.model.Motorista;
import com.example.frota.domain.motorista.service.MotoristaService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/motorista")
@CrossOrigin("*")
public class MotoristaController {
	
	private final Set<String> CHAVES_VALIDAS = Set.of(
            "cco123",
            "azul123",
            "frota-secret-key"
    );
	
	@Autowired
	private MotoristaService motoristaService;
	
	@GetMapping
    public String carregarPaginaListagem(Model model) {
        model.addAttribute("listaTipos", motoristaService.procurarTodos());
        return "/motorista";
    }
	
	@GetMapping("/{id}")
    public String mostrarFormulario(@RequestParam(required = false) Long id, Model model) {
		if(id != null) {
			Motorista motorista = motoristaService.procurarPorId(id)
					.orElseThrow(() -> new EntityNotFoundException("Motorista não encontrado"));
			model.addAttribute("motorista", motorista);
		}
		return "/motorista";       
    }
	
	@PostMapping
    @Transactional
    public ResponseEntity<?> criar(
    		@RequestHeader("X-API-KEY") String apiKey,
            @RequestBody @Valid AtualizacaoMotorista dto) throws Exception {

        if (!CHAVES_VALIDAS.contains(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"erro\":\"Chave API inválida\"}");
        }

        try {
            motoristaService.save(new Motorista(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("{\"erro\":\"Motorista não encontrado\"}");
        }
    }

    @PutMapping
    @Transactional
    public ResponseEntity<AtualizacaoMotorista> atualizar(@RequestBody @Valid AtualizacaoMotorista dto) {
        if (dto.id() == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
        	motoristaService.atualizarMotorista(dto);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            if (motoristaService.procurarPorId(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            motoristaService.apagarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
