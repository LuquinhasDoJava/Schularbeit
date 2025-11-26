package com.example.frota.caminhao;

import com.example.frota.marca.Marca;
import com.example.frota.manutencao.Manutencao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "caminhao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Caminhao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private double cargaMaxima;

    private double quilometragemAtual;
    private double quilometragemUltimaManutencao;
    private double quilometragemUltimaTrocaPneus;
    private LocalDate dataProximaManutencao;

    @Enumerated(EnumType.STRING)
    private StatusManutencao statusManutencao;

    @OneToMany(mappedBy = "caminhao", cascade = CascadeType.ALL)
    private List<Manutencao> manutencoes;

    @Transient
    public double getVolume() {
        return altura * largura * comprimento;
    }

    @Transient
    public boolean isPrecisaManutencaoRotina() {
        return (quilometragemAtual - quilometragemUltimaManutencao) >= 10000;
    }

    @Transient
    public boolean isPrecisaTrocaPneus() {
        return (quilometragemAtual - quilometragemUltimaTrocaPneus) >= 70000;
    }

    public enum StatusManutencao {
        EM_DIA, MANUTENCAO_PROXIMA, EM_MANUTENCAO
    }
}