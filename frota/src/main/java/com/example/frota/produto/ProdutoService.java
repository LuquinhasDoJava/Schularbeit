package com.example.frota.produto;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.frota.caixa.Caixa;
import com.example.frota.caixa.CaixaService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CaixaService caixaService;

    @Autowired
    private ProdutoMapper produtoMapper;

    public Produto salvarOuAtualizar(AtualizacaoProduto dto) {
        // Valida se a caixa existe
        Caixa caixa = caixaService.procurarPorId(dto.caixaId())
                .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada com ID: " + dto.caixaId()));

        if (dto.id() != null) {
            // atualizando: Busca existente e atualiza
            Produto existente = produtoRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + dto.id()));
            produtoMapper.updateEntityFromDto(dto, existente);
            existente.setCaixa(caixa); // Atualiza a caixa
            return produtoRepository.save(existente);
        } else {
            // criando: Novo produto
            Produto novoProduto = produtoMapper.toEntityFromAtualizacao(dto);
            novoProduto.setCaixa(caixa); // Define a caixa completa
            return produtoRepository.save(novoProduto);
        }
    }

    public List<Produto> procurarTodos() {
        return produtoRepository.findAll(Sort.by("id").ascending());
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void apagarPorId(Integer id) {
    	try {
            produtoRepository.deleteById(id);
            produtoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException(
                "Não é possível deletar este produto, existem encomendas vinculadas a ele.", e);
        }
    }

    public Optional<Produto> procurarPorId(Integer id) {
        return produtoRepository.findById(id);
    }
}