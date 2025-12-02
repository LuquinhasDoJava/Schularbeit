package com.example.frota.domain.produto.model;

import com.example.frota.application.dto.produto.AtualizacaoProduto;
import com.example.frota.application.dto.produto.CadastroProduto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Produto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "produto_id")
	private Long id;
	
	private String nome;
	private Double peso;
	private Double preco;
	
	public Produto(CadastroProduto dados) {
		this.nome = dados.nome();
		this.peso = dados.peso();
		this.preco = dados.preco();
	}
	
	public Produto(AtualizacaoProduto dados) {
		this.nome = dados.nome();
		this.peso = dados.peso();
		this.preco = dados.preco();
	}
	
	public void atualizarInformacoes(AtualizacaoProduto dados) {
		if(dados.nome() != null)
			this.nome = dados.nome();
		if(dados.peso() != 0)
			this.peso = dados.peso();
		if(dados.preco() != 0)
			this.preco = dados.preco();
	}

}
