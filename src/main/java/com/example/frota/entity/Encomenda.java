package com.example.frota.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "encomenda")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Encomenda {

    @Id
    @Column(name = "encomenda_id", nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Caixa caixa;



    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Caminhao caminhao;

    @Column(nullable = false)
    private double pesoCubado;

    @Column(nullable = false)
    private double peso;

    @Column(nullable = false)
    private double distanciaKm;

    @Column(nullable = false)
    private double pesoCobranca;

    @Column(nullable = false)
    private double preco;

    @OneToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Transient
    public double getPesoCubado() {
        return caixa.getVolume() * caminhao.getFatorCubagem();
    }

    @Transient
    public double getPesoCobranca() {
        return Math.max(peso, getPesoCubado());
    }
}
