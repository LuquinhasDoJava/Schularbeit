package com.example.frota.encomenda;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.example.frota.caixa.Caixa;
import com.example.frota.caixa.CaixaService;
import com.example.frota.produto.ProdutoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.frota.produto.Produto;
import com.example.frota.produto.ProdutoService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EncomendaService {

    @Autowired
    private EncomendaRepository encomendaRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CaixaService caixaService;

    @Autowired
    private ProdutoMapper produtoMapper;

    @Autowired
    private EncomendaMapper encomendaMapper;

    public Encomenda salvarOuAtualizar(AtualizacaoEncomenda dto) {
        // Valida se o produto existe
        Produto produto = produtoService.procurarPorId(dto.produtoId())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + dto.produtoId()));

        if (dto.id() != null) {
            // atualizando: Busca existente e atualiza
            Encomenda existente = encomendaRepository.findById(dto.id())
                    .orElseThrow(() -> new EntityNotFoundException("Encomenda não encontrada com ID: " + dto.id()));
            encomendaMapper.updateEntityFromDto(dto, existente);
            existente.setProduto(produto); // Atualiza o produto
            return encomendaRepository.save(existente);
        } else {
            // criando: Nova encomenda
            Encomenda novaEncomenda = encomendaMapper.toEntityFromAtualizacao(dto);
            novaEncomenda.setProduto(produto); // Define o produto completo
            return encomendaRepository.save(novaEncomenda);
        }
    }

    public List<Encomenda> procurarTodos() {
        return encomendaRepository.findAll(Sort.by("id").ascending());
    }

    public void apagarPorId(Integer id) {
        encomendaRepository.deleteById(id);
    }

    public Optional<Encomenda> procurarPorId(Integer id) {
        return encomendaRepository.findById(id);
    }

    public Encomenda criarEncomendaAutomatica(Integer produtoId, Long caixaId, String origem,
                                              String destino, Double distancia, Double preco) {
        // Busca o produto
        Produto produto = produtoService.procurarPorId(produtoId)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        // Busca a caixa
        Caixa caixa = caixaService.procurarPorId(caixaId)
                .orElseThrow(() -> new EntityNotFoundException("Caixa não encontrada"));

        // Atualiza o produto com a caixa selecionada
        produto.setCaixa(caixa);
        produtoService.salvarOuAtualizar(produtoMapper.toAtualizacaoDto(produto));

        // Cria a encomenda
        Encomenda encomenda = new Encomenda();
        encomenda.setProduto(produto);
        encomenda.setDestino(destino);
        encomenda.setDistancia(BigDecimal.valueOf(distancia));
        encomenda.setPreco(BigDecimal.valueOf(preco));

        return encomendaRepository.save(encomenda);
    }
}