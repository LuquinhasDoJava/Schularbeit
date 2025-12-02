package com.example.frota.domain.caixa.model;

import com.example.frota.application.dto.caixa.AtualizacaoCaixa;
import com.example.frota.application.dto.caixa.CadastroCaixa;
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
@Table(name = "caixa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Caixa {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "caixa_id")
	private Long id;
	
	private Double altura;
	private Double largura;
	private Double comprimento;
	private String material;
	private Double limitePeso;
	
	public Caixa(CadastroCaixa dados) {
		this.altura = dados.altura();
		this.largura = dados.largura();
		this.comprimento = dados.comprimento();
		this.material = dados.material();
		this.limitePeso = dados.limitePeso();
	}
	public Caixa(AtualizacaoCaixa dados) {
		this.altura = dados.altura();
		this.largura = dados.largura();
		this.comprimento = dados.comprimento();
		this.material = dados.material();
		this.limitePeso = dados.limitePeso();
	}
	
	public void atualizarInformacoes(AtualizacaoCaixa dados) {
		if(dados.altura() != 0)
			this.altura = dados.altura();
		if(dados.largura() != 0)
			this.largura = dados.largura();
		if (dados.comprimento() != 0)
			this.comprimento = dados.comprimento();
		if (dados.material() != null)
			this.material = dados.material();
		if (dados.limitePeso() != 0)
			this.limitePeso = dados.limitePeso();
	}
	
}
