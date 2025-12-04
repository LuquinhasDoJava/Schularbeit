package com.example.frota.produto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.frota.caixa.CaixaService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.math.BigDecimal;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoMapper produtoMapper;

    @Autowired
    private CaixaService caixaService;

    @GetMapping
    public String carregaPaginaListagem(Model model) {
        model.addAttribute("listaProdutos", produtoService.procurarTodos());
        return "produto/listagem";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(@RequestParam(required = false) Integer id, Model model) {
        AtualizacaoProduto dto;
        if (id != null) {
            // edição: Carrega dados existentes
            Produto produto = produtoService.procurarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
            dto = produtoMapper.toAtualizacaoDto(produto);
        } else {
            // criação: DTO vazio
            dto = new AtualizacaoProduto(null, null, null, null, null, null,null);
        }
        model.addAttribute("produto", dto);
        model.addAttribute("caixas", caixaService.procurarTodos());
        return "produto/formulario";
    }

    @GetMapping("/formulario/{id}")
    public String carregaPaginaFormulario(@PathVariable("id") Integer id, Model model,
                                          RedirectAttributes redirectAttributes) {
        AtualizacaoProduto dto;
        try {
            if (id != null) {
                Produto produto = produtoService.procurarPorId(id)
                        .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
                dto = produtoMapper.toAtualizacaoDto(produto);
                model.addAttribute("produto", dto);
                model.addAttribute("caixas", caixaService.procurarTodos());
            }
            return "produto/formulario";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/produto";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("produto") @Valid AtualizacaoProduto dto,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("caixas", caixaService.procurarTodos());
            return "produto/formulario";
        }
        try {
            Produto produtoSalvo = produtoService.salvarOuAtualizar(dto);
            String mensagem = dto.id() != null
                    ? "Produto '" + produtoSalvo.getId() + "' atualizado com sucesso!"
                    : "Produto '" + produtoSalvo.getId() + "' criado com sucesso!";
            redirectAttributes.addFlashAttribute("message", mensagem);
            return "redirect:/produto";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            model.addAttribute("caixas", caixaService.procurarTodos());
            return "redirect:/produto/formulario" + (dto.id() != null ? "?id=" + dto.id() : "");
        }
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String deleteProduto(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            produtoService.apagarPorId(id);
            redirectAttributes.addFlashAttribute("message", "O produto " + id + " foi apagado!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/produto";
    }
}