package com.example.frota.controller;

import com.example.frota.dto.AtualizacaoCaixa;
import com.example.frota.dto.CadastroCaixa;
import com.example.frota.service.CaixaService;
import com.example.frota.entity.Caixa;
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
@RequestMapping("/caixa")
public class CaixaController {

    @Autowired
    private CaixaService caixaService;

    @GetMapping
    public String carregaPaginaListagem(Model model){
        model.addAttribute("lista",caixaService.procurarTodos() );
        return "caixa/listagem";
    }
    @GetMapping ("/formulario")
    public String carregaPaginaFormulario(Long id, Model model) {
        if(id != null) {
            var caixa = caixaService.procurarPorId(id);
            model.addAttribute("caixa", caixa);
        }
        return "caixa/formulario";
    }
    @DeleteMapping
    @Transactional
    public String excluir (Long id) {
        caixaService.apagarPorId(id);
        return "redirect:caixa";
    }
    @PostMapping
    @Transactional
    public String cadastrar (@Valid CadastroCaixa dados) {
        caixaService.salvar(new Caixa(dados));
        return "redirect:caixa";
    }
    @PutMapping
    @Transactional
    public String atualizar (AtualizacaoCaixa dados) {
        caixaService.atualizarCaixa(dados);
        return "redirect:caixa";
    }
}