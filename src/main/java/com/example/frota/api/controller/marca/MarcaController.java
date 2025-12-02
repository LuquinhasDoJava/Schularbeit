package com.example.frota.api.controller.marca;

import com.example.frota.application.dto.marca.DadosAtualizacaoMarca;
import com.example.frota.application.dto.marca.DadosCadastroMarca;
import com.example.frota.domain.marca.model.Marca;
import com.example.frota.domain.marca.service.MarcaService;

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
@RequestMapping("/marca")
@CrossOrigin("*")
public class MarcaController {
	
	private final Set<String> CHAVES_VALIDAS = Set.of(
            "cco123",
            "azul123",
            "frota-secret-key"
    );
 
	@Autowired
	private MarcaService marcaService;
 
	@GetMapping              
	public String carregaPaginaListagem(Model model){
		model.addAttribute("lista",marcaService.procurarTodos() );
		return "marca/listagem";              
	}
	
	@GetMapping("/{id}")        
	public String mostraFormulario(@RequestParam(required = false) Long id, Model model) {
		if(id != null) {
			Marca marca = marcaService.buscar(id)
					.orElseThrow(() -> new EntityNotFoundException("Marca não encontrada"));;
			model.addAttribute("marca", marca);
		}
		return "/marca";     
	}
	
	@PostMapping
    @Transactional
    public ResponseEntity<?> criar(
    		@RequestHeader("X-API-KEY") String apiKey,
            @RequestBody @Valid DadosCadastroMarca dto) throws Exception {

        if (!CHAVES_VALIDAS.contains(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"erro\":\"Chave API inválida\"}");
        }

        try {
            marcaService.save(new Marca(dto));
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("{\"erro\":\"Marca não encontrado\"}");
        }
    }
	
	@PutMapping
    @Transactional
    public ResponseEntity<DadosAtualizacaoMarca> atualizar(@RequestBody @Valid DadosAtualizacaoMarca dto) {
        if (dto.id() == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
        	marcaService.atualizarMarca(dto);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
	
	@DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            if (marcaService.procurarPorId(id).isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            marcaService.apagarPorId(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}