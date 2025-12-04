package com.example.frota.caixa;

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

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/caixa")
public class CaixaController {

    @Autowired
    private CaixaService caixaService;

    @Autowired
    private CaixaMapper caixaMapper;

    @GetMapping
    public String carregaPaginaListagem(Model model) {
        model.addAttribute("listaCaixas", caixaService.procurarTodos());
        return "caixa/listagem";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(@RequestParam(required = false) Long id, Model model) {
        AtualizacaoCaixa dto;
        if (id != null) {
            // edição: Carrega dados existentes
            Caixa caixa = caixaService.procurarPorId(id)
                    .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada"));
            dto = caixaMapper.toAtualizacaoDto(caixa);
        } else {
            // criação: DTO vazio
            dto = new AtualizacaoCaixa(null, null, null, null, null, "");
        }
        model.addAttribute("caixa", dto);
        return "caixa/formulario";
    }

    @GetMapping("/formulario/{id}")
    public String carregaPaginaFormulario(@PathVariable("id") Long id, Model model,
                                          RedirectAttributes redirectAttributes) {
        AtualizacaoCaixa dto;
        try {
            if (id != null) {
                Caixa caixa = caixaService.procurarPorId(id)
                        .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada"));
                dto = caixaMapper.toAtualizacaoDto(caixa);
                model.addAttribute("caixa", dto);
            }
            return "caixa/formulario";
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/caixa";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute("caixa") @Valid AtualizacaoCaixa dto,
                         BindingResult result,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        if (result.hasErrors()) {
            return "caixa/formulario";
        }
        try {
            Caixa caixaSalva = caixaService.salvarOuAtualizar(dto);
            String mensagem = dto.id() != null
                    ? "Caixa '" + caixaSalva.getId() + "' atualizada com sucesso!"
                    : "Caixa '" + caixaSalva.getId() + "' criada com sucesso!";
            redirectAttributes.addFlashAttribute("message", mensagem);
            return "redirect:/caixa";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/caixa/formulario" + (dto.id() != null ? "?id=" + dto.id() : "");
        }
    }

    @GetMapping("/delete/{id}")
    @Transactional
    public String deleteCaixa(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            caixaService.apagarPorId(id);
            redirectAttributes.addFlashAttribute("message", "A caixa " + id + " foi apagada!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/caixa";
    }
}