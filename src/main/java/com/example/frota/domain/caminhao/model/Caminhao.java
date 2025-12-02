package com.example.frota.domain.caminhao.model;

import com.example.frota.application.dto.caminhao.AtualizacaoCaminhao;
import com.example.frota.application.dto.caminhao.CadastroCaminhao;
import org.hibernate.annotations.Formula;

import com.example.frota.domain.marca.model.Marca;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "caminhao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Caminhao {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "caminhao_id")
	private Long id;
	private String modelo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "marca_id", referencedColumnName = "marca_id")
	private Marca marca;
	private String placa;
	private double cargaMaxima;
	private int ano;
	private double comprimento;
	private double largura;
	private double altura;
	@Transient
	private final double fatorCubagem = 300.0;

	@Formula("CAST((comprimento * largura * altura) * 300.0 AS DECIMAL(10, 2))")
	@Column(name = "metragem_cubica")
    private double metragemCubica;

	public Caminhao(CadastroCaminhao dados, Marca marca) {
		this.modelo = dados.modelo();
		this.placa = dados.placa();
		this.cargaMaxima = dados.cargaMaxima();
		this.marca = marca;
		this.ano= dados.ano();
		this.comprimento = dados.comprimento();
		this.largura = dados.largura();
		this.altura = dados.altura();
	}
	public Caminhao(AtualizacaoCaminhao dados, Marca marca) {
		this.modelo = dados.modelo();
		this.placa = dados.placa();
		this.cargaMaxima = dados.cargaMaxima();
		this.marca = marca;
		this.ano= dados.ano();
		this.comprimento = dados.comprimento();
		this.largura = dados.largura();
		this.altura = dados.altura();
	}
	
	public void atualizarInformacoes(AtualizacaoCaminhao dados, Marca marca) {
		if (dados.modelo() != null )
			this.modelo = dados.modelo();
		if (dados.placa() != null)
			this.placa =dados.placa();
		if (dados.cargaMaxima() != 0)
			this.cargaMaxima = dados.cargaMaxima();
		if (marca != null)
			this.marca = marca;
		if (dados.ano() != 0)
			this.ano = dados.ano();
		if (dados.comprimento() != 0)
			this.comprimento = dados.comprimento();
		if(dados.largura() != 0)
			this.largura = dados.largura();
		if(dados.altura() != 0)
			this.altura = dados.altura();
	}
	
}
