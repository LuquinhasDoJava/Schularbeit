package com.example.frota.entity;

import java.time.LocalDate;

import com.example.frota.dto.CadastroEncomenda;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Encomenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "encomenda_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "caixa_id", nullable = false)
    private Caixa caixa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "caminhao_id", nullable = false)
    private Caminhao caminhao;

    @OneToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private double pesoReal;

    @Column(nullable = false)
    private double distanciaKm;
    
    private String descricao;
    
    private Double valor;
    
    private LocalDate dataEntrega;

    public Encomenda(@Valid CadastroEncomenda dados) {
    }


    @Transient
    public double getPesoCubado() {
        return caixa.getVolume() * caminhao.getFatorCubagem();
    }

    @Transient
    public double getPesoCobranca() {
        return Math.max(pesoReal, getPesoCubado());
    }

    @Transient
    public double getPreco() {
        double taxaPorKgKm = 2.50;
        return getPesoCobranca() * distanciaKm * taxaPorKgKm;
    }
}