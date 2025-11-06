package com.example.frota.caixa;

import jakarta.persistence.*;
import jakarta.validation.Valid;
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
	private long id;
	
	private int altura;
	private int largura;
	private int comprimento;
    private double pesoMaximo;
	private String material;

    @Transient
    private double volume;

    public double getVolume() {
        return altura * largura * comprimento;
    }
    
	public Caixa(CadastroCaixa dados) {
		this.comprimento = dados.comprimento();
		this.largura = dados.largura();
		this.altura = dados.altura();
		this.pesoMaximo = dados.pesoMaximo();
		this.material = dados.material();
	}
	
	public void atualizarInformacoes(@Valid AtualizacaoCaixa dados) {
		if (dados.comprimento() != null) {
			this.comprimento = dados.comprimento();
			this.largura = dados.largura();
			this.altura = dados.altura();
			this.pesoMaximo = dados.pesoMaximo();
			this.material = dados.material();
		}
	}
}
