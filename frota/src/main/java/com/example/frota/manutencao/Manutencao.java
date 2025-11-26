package com.example.frota.manutencao;

import com.example.frota.caminhao.Caminhao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "manutencao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manutencao_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caminhao_id")
    private Caminhao caminhao;

    private LocalDate dataManutencao;
    private double quilometragem;

    @Enumerated(EnumType.STRING)
    private TipoManutencao tipoManutencao;

    private String descricao;
    private double custo;

    public enum TipoManutencao {
        ROTINA, PNEUS, CORRETIVA
    }
}