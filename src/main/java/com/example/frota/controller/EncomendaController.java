package com.example.frota.controller;

import com.example.frota.dto.AtualizacaoEncomenda;
import com.example.frota.dto.CadastroEncomenda;
import com.example.frota.entity.Encomenda;
import com.example.frota.mapper.EncomendaMapper;
import com.example.frota.service.EncomendaService;
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
@RequestMapping("/encomenda")
public class EncomendaController {

    @Autowired
    private EncomendaService encomendaService;

    @Autowired
    private EncomendaMapper encomendaMapper;

    @GetMapping
    public String carregaPaginaListagem(Model model){
        model.addAttribute("lista", encomendaService.procurarTodos());
        return "encomenda/listagem";
    }

    @GetMapping("/formulario")
    public String carregaPaginaFormulario(@RequestParam(required = false) Long id, Model model) {
        if(id != null) {
            var encomenda = encomendaService.procurarPorId(id);
            if(encomenda.isPresent()) {
                model.addAttribute("encomenda", encomendaMapper.toCadastroDto(encomenda.get()));
            }
        }
        return "encomenda/formulario";
    }

    @DeleteMapping
    @Transactional
    public String excluir(@RequestParam Long id) {
        encomendaService.apagarPorId(id);
        return "redirect:/encomenda";
    }

    @PostMapping
    @Transactional
    public String cadastrar(@Valid CadastroEncomenda dados) {
        Encomenda encomenda = encomendaMapper.toEntityFromCadastro(dados);
        encomendaService.salvar(encomenda);
        return "redirect:/encomenda";
    }

    @PutMapping
    @Transactional
    public String atualizar(@Valid AtualizacaoEncomenda dados) {
        var encomendaOptional = encomendaService.procurarPorId(dados.id());

        if(encomendaOptional.isPresent()) {
            Encomenda encomenda = encomendaOptional.get();

            // Cria um DTO de cadastro para reutilizar o mapper
            CadastroEncomenda cadastroDto = new CadastroEncomenda(
                    dados.caixaId() != null ? dados.caixaId() : encomenda.getCaixa().getId(),
                    dados.caminhaoId() != null ? dados.caminhaoId() : encomenda.getCaminhao().getId(),
                    dados.produtoId() != null ? dados.produtoId() : encomenda.getProduto().getId(),
                    dados.pesoReal() != null ? dados.pesoReal() : encomenda.getPesoReal(),
                    dados.distanciaKm() != null ? dados.distanciaKm() : encomenda.getDistanciaKm()
            );

            encomendaMapper.updateEntityFromDto(cadastroDto, encomenda);
            encomendaService.salvar(encomenda);
        }

        return "redirect:/encomenda";
    }
}