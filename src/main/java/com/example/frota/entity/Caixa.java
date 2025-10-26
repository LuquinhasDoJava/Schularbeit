package com.example.frota.entity;

import com.example.frota.dto.CadastroCaixa;
import jakarta.persistence.*;
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
}
