package com.example.frota.service;

import java.util.List;
import java.util.Optional;

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
	        .orElseThrow(() -> new EntityNotFoundException("Produto não encontrada"));
	    produto.atualizarInformacoes(dados);
	}
	public Optional<Produto> procurarPorId( Long id) {
		return produtoRepository.findById(id);
	}
	public List<Produto> procurarTodos(){
		return produtoRepository.findAll();
	}
}
