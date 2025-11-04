package com.example.frota.entity;

import com.example.frota.dto.AtualizacaoProduto;
import com.example.frota.dto.CadastroProduto;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private double peso;

    @Column(nullable = false)
    private double altura;

    @Column(nullable = false)
    private double largura;

    @Column(nullable = false)
    private double comprimento;

    public Produto(CadastroProduto dado){
        this.peso = dado.peso();
        this.altura = dado.altura();
        this.largura = dado.largura();
        this.comprimento = dado.comprimento();
    }

    public void atualizarInformacoes(@Valid AtualizacaoProduto dados){
    	if (dados.peso() > 0)
			this.peso = dados.peso();
		if (dados.altura() > 0)
			this.altura =dados.altura();
		if (dados.largura() > 0)
			this.largura = dados.largura();
		if (dados.comprimento() > 0)
			this.comprimento = dados.comprimento();
    }
}


