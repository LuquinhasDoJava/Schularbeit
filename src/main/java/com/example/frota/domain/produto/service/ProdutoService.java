package com.example.frota.domain.produto.service;

import java.util.List;
import java.util.Optional;

import com.example.frota.application.dto.produto.AtualizacaoProduto;
import com.example.frota.domain.produto.model.Produto;
import com.example.frota.domain.produto.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;

	public Produto save(Produto produto) {
		return produtoRepository.save(produto);
	}

    public List<Produto> procurarProdutosSemSolicitacao(){return produtoRepository.procurarProdutosSemSolicitacao();}

	public List<Produto> procurarTodos() {
		return produtoRepository.findAll(Sort.by("nome").ascending());
	}

	public void apagarPorId(Long id) {
		produtoRepository.deleteById(id);
	}

	public Optional<Produto> procurarPorId(Long id) {
		return produtoRepository.findById(id);
	}
	
	public void atualizarProduto(AtualizacaoProduto dados) {
	    Produto produto = produtoRepository.findById(dados.id())
	        .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
	    produto .atualizarInformacoes(dados);
	}

}
