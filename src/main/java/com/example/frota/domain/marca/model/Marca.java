package com.example.frota.domain.marca.model;

import com.example.frota.application.dto.marca.DadosAtualizacaoMarca;
import com.example.frota.application.dto.marca.DadosCadastroMarca;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "marca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Marca {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "marca_id")
	private long id;
	private String nome;
	private String pais;
	
	public Marca(DadosCadastroMarca dados) {
		this.nome = dados.nome();
		this.pais = dados.pais();
	}
	

	
	public void atualizarInformacoes(@Valid DadosAtualizacaoMarca dados) {
		if (dados.nome() != null) {
			this.nome = dados.nome();
			this.pais = dados.pais();
		}
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	
	
}