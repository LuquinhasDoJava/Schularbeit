package com.example.frota.entity;

import com.example.frota.dto.AtualizacaoCaixa;
import com.example.frota.dto.CadastroCaixa;
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
	private Long id;

    @Column(nullable = false)
	private double altura;

    @Column(nullable = false)
	private double largura;

    @Column(nullable = false)
	private double comprimento;

    @Column(nullable = false)
	private String material;

    @Column(nullable = false)
	private double limitePeso;

    @Transient
    public double getVolume() {
        return altura * comprimento * largura;
    }


    public Caixa(double largura, double altura, double comprimento){
        this.altura = altura;
        this.comprimento = comprimento;
        this.largura = largura;
    }

    public void atualizarInformacoes(@Valid AtualizacaoCaixa dados){
        if(dados.altura() > 0) this.altura = dados.altura();
        if(dados.largura() > 0) this.largura = dados.largura();
        if(dados.comprimento() > 0) this.comprimento = dados.altura();

    }

    public Caixa (CadastroCaixa cadastroCaixa){
        this.altura = cadastroCaixa.altura();
        this.largura = cadastroCaixa.largura();
        this.comprimento = cadastroCaixa.comprimento();
        this.material = cadastroCaixa.material();
        this.limitePeso = cadastroCaixa.limitePeso();
    }

}
