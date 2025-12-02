package com.example.frota.domain.viagem.model;

import com.example.frota.domain.caminhao.model.Caminhao;
import com.example.frota.domain.motorista.model.Motorista;
import com.example.frota.domain.solicitacao.model.Solicitacao;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "viagem")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Viagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "caminhao_id")
    private Caminhao caminhao;

    @ManyToOne
    @JoinColumn(name = "motorista_id")
    private Motorista motorista;

    @OneToMany
    @JoinTable(name = "viagem_solicitacoes",
        joinColumns = @JoinColumn(name = "viagem_id"),
        inverseJoinColumns = @JoinColumn(name = "solicitacao_id"))
    private List<Solicitacao> entregas;

    private LocalDateTime dataSaida;
    private LocalDateTime dataChegada;
    
    private Double kmSaida;
    private Double kmChegada;
    private Double totalCombustivelLitros;

    public Double getDistanciaPercorrida() {
        if (kmChegada!= null && kmSaida!= null) {
            return kmChegada - kmSaida;
        }
        return 0.0;
    }
}