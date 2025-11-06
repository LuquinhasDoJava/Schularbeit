package com.example.frota.caixa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/caixa")
public class CaixaController {

	@Autowired
	private CaixaService caixaService;

	@GetMapping              
	public String carregaPaginaListagem(Model model){ 
		model.addAttribute("listaCaixas",caixaService.procurarTodos() );
		return "caixa/listagem";              
	} 
	  @GetMapping("/formulario")             
	    public String carregaPaginaFormulario(@RequestParam(required = false) Long id, Model model) {
	        if (id != null) {
	            // Edição: Carrega a entidade Caixa diretamente
	            Caixa caixa = caixaService.procurarPorId(id)
	                    .orElseThrow(() -> new RuntimeException("Caixa não encontrado"));
	            model.addAttribute("caixa", caixa);
	        } else {
	            // Criação: Nova entidade Caixa
	            model.addAttribute("caixa", new Caixa());
	        }
	        return "caixa/formulario";     
	    }
	@DeleteMapping
	@Transactional
	public String excluir (Long id) {
		caixaService.apagarPorId(id);
		return "redirect:caixa";
	}
	// Método para gravar/atualizar o formulário 
	@PostMapping("/salvar")
	@Transactional
	public String cadastrar (@Valid CadastroCaixa dados) {
		caixaService.salvar(new Caixa(dados));
		return "redirect:formulario";
	}
	@PutMapping
	@Transactional
	public String atualizar (AtualizacaoCaixa dados) {
		caixaService.atualizarCaixa(dados);
		return "redirect:caixa";
	}
}
