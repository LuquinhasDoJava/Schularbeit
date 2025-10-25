package com.example.frota.controller;

import com.example.frota.dto.AtualizacaoMarca;
import com.example.frota.dto.CadastroMarca;
import com.example.frota.service.MarcaService;
import com.example.frota.entity.Marca;
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
@RequestMapping("/marca")
public class MarcaController {

	@Autowired
	private MarcaService marcaService;

	@GetMapping              
	public String carregaPaginaListagem(Model model){ 
		model.addAttribute("lista",marcaService.procurarTodos() );
		return "marca/listagem";              
	} 
	@GetMapping ("/formulario")             
	public String carregaPaginaFormulario(Long id, Model model) {
		if(id != null) {
			var marca =marcaService.procurarPorId(id);
			model.addAttribute("marca", marca);
		}
		return "marca/formulario";     
	}
	@DeleteMapping
	@Transactional
	public String excluir (Long id) {
		marcaService.apagarPorId(id);
		return "redirect:marca";
	}
	// Método para gravar/atualizar o formulário 
	@PostMapping
	@Transactional
	public String cadastrar (@Valid CadastroMarca dados) {
		marcaService.salvar(new Marca(dados));
		return "redirect:marca";
	}
	@PutMapping
	@Transactional
	public String atualizar (AtualizacaoMarca dados) {
		marcaService.atualizarMarca(dados);
		return "redirect:marca";
	}
}
