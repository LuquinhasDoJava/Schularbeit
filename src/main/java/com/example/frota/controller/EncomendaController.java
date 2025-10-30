package com.example.frota.controller;

import com.example.frota.dto.AtualizacaoEncomenda;
import com.example.frota.dto.CadastroEncomenda;
import com.example.frota.service.EncomendaService;
import com.example.frota.entity.Encomenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/encomenda")
public class EncomendaController {

	@Autowired
	private EncomendaService encomendaService;

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
	@PostMapping
	@Transactional
	public String cadastrar (@Valid CadastroEncomenda dados) {
		encomendaService.salvar(new Encomenda(dados));
		return "redirect:encomenda";
	}
	@PutMapping
	@Transactional
	public String atualizar (AtualizacaoEncomenda dados) {
		encomendaService.atualizarEncomenda(dados);
		return "redirect:encomenda";
	}
}
