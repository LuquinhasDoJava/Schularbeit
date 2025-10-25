package com.example.frota.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "encomenda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Encomenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "encomenda_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caixa_id", nullable = false)
    private Caixa caixa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caminha_id", nullable = false)
    private Caminhao caminhao;

    @Column(nullable = false)
    private double pesoCubado;

    @Column(nullable = false)
    private double peso;

    @Column(nullable = false)
    private double preco;

    @Transient
    public double getPesoCubado() {
        return caixa.getVolume()/peso;
    }
}
