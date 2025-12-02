package com.example.frota.domain.manutencao.model;

import com.example.frota.domain.caminhao.model.Caminhao;
import jakarta.persistence.*;
import lombok.*;
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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "caminhao_id")
    private Caminhao caminhao;

    private LocalDate data;
    private Double kmNoMomento;
    private Double custo;
    
    @Enumerated(EnumType.STRING)
    private TipoManutencao tipo; // PREVENTIVA_10K, TROCA_PNEU_70K, CORRETIVA

    private String descricao; // Ex: "Troca de óleo, filtros e pastilhas"
}

enum TipoManutencao {
    REVISAO_BASICA, // 10.000 km
    TROCA_PNEUS,    // 70.000 km
    CORRETIVA
}