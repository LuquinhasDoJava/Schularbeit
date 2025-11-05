package com.example.frota.caminhao;

import com.example.frota.marca.Marca;

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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "caminhao_id")
	private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id", referencedColumnName = "marca_id")
    private Marca marca;

    private String modelo;

    private int ano;

    private String placa;

    private double altura;

    private double largura;

    private double comprimento;

    @Transient
    private double volume;

    private double cargaMaxima;

    public double getVolume() {
        return altura * largura * comprimento;
    }
}
