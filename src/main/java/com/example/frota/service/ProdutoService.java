package com.example.frota.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.frota.dto.AtualizacaoProduto;
import com.example.frota.entity.Produto;
import com.example.frota.repository.ProdutoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository produtoRepository;


    public Produto salvar(Produto produto) {
        return produtoRepository.save(produto);
    }
    public void apagarPorId (Long id) {
        produtoRepository.deleteById(id);
    }
    public void atualizarProduto(AtualizacaoProduto dados) {
        Produto produto = produtoRepository.findById(dados.id())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        produto.atualizarInformacoes(dados);
    }


}