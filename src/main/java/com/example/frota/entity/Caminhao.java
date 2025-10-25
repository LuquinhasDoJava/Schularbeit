package com.example.frota.entity;

import com.example.frota.dto.AtualizacaoCaminhao;
import com.example.frota.dto.CadastroCaminhao;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caminhao_id")
	private Long id;

	@Column(nullable = false, length = 30)
    private String modelo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "marca_id", referencedColumnName = "marca_id")
	private Marca marca;

	@Column(length = 7, nullable = false)
    private String placa;

	@Column(name = "carga_maxima", nullable = false)
    private double cargaMaxima;

	@Column(nullable = false)
    private int ano;

	@Column(nullable = false)
    private double altura;

	@Column(nullable = false)
    private double comprimento;

	@Column(nullable = false)
    private double largura;

	@Column(name = "volume", nullable = false)
    private double volume;

    @Transient
    public double getVolume(){
        return altura * comprimento * largura;
    }
}
