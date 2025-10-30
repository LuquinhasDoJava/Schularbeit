package com.example.frota.controller;

import com.example.frota.dto.AtualizacaoEncomenda;
import com.example.frota.service.EncomendaService;
import com.example.frota.service.ProdutoService;
import com.example.frota.entity.Encomenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/encomenda")
public class EncomendaController {

	@Autowired
	private EncomendaService encomendaService;
	@Autowired
	private ProdutoService produtoService;

	@GetMapping              
	public String carregaPaginaListagem(Model model){ 
		model.addAttribute("lista",encomendaService.procurarTodos() );
		return "encomenda/listagem";              
	} 
	@GetMapping ("/formulario")             
	public String carregaPaginaFormulario(Long id, Model model) {
		if(id != null) {
			var encomenda = encomendaService.procurarPorId(id);
			model.addAttribute("encomenda", encomenda);
		}
		return "encomenda/formulario";     
	}
	@DeleteMapping
	@Transactional
	public String excluir (Long id) {
		encomendaService.apagarPorId(id);
		return "redirect:encomenda";
	}
	@PostMapping("/salvar")
    public String salvar(@ModelAttribute("encomenda") @Valid AtualizacaoEncomenda dto,
                        BindingResult result,
                        RedirectAttributes redirectAttributes,
                        Model model) {
		if (result.hasErrors()) {
	        // Recarrega dados necessários para mostrar erros
	        model.addAttribute("produtos", produtoService.procurarTodos());
	        return "encomenda/formulario";
	    }
	    try {
	        Encomenda encomendaSalvo = encomendaService.salvarOuAtualizar(dto);
	        String mensagem = dto.id() != null 
	            ? "Encomenda '" + encomendaSalvo.getId() + "' atualizada com sucesso!"
	            : "Encomenda '" + encomendaSalvo.getId() + "' criada com sucesso!";
	        redirectAttributes.addFlashAttribute("message", mensagem);
	        return "redirect:/encomenda";
	    } catch (EntityNotFoundException e) {
	        redirectAttributes.addFlashAttribute("error", e.getMessage());
	        return "redirect:/encomenda/formulario" + (dto.id() != null ? "?id=" + dto.id() : "");
	    }
	}
}
