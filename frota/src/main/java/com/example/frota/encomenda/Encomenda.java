package com.example.frota.encomenda;

import com.example.frota.StatusEntrega;
import com.example.frota.produto.Produto;
import com.example.frota.caminhao.Caminhao;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "encomenda") // CORREÇÃO: nome correto da tabela
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Encomenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private String destino;
    private String origem;
    private BigDecimal distancia;
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    private StatusEntrega statusEntrega;

    private LocalDateTime dataSolicitacao;
    private LocalDateTime dataColeta;
    private LocalDateTime dataProcessamento;
    private LocalDateTime dataEnvio;
    private LocalDateTime dataEntrega;

    private LocalTime horarioColeta;

    @ManyToOne
    @JoinColumn(name = "caminhao_id")
    private Caminhao caminhao;

    private String codigoRastreio;
    private Double latitudeAtual;
    private Double longitudeAtual;

    private Integer pontuacao;
    private String comentarioFeedback;

    @PrePersist
    public void prePersist() {
        if (this.dataSolicitacao == null) {
            this.dataSolicitacao = LocalDateTime.now();
        }
        if (this.statusEntrega == null) {
            this.statusEntrega = StatusEntrega.SOLICITADA;
        }
        if (this.codigoRastreio == null) {
            this.codigoRastreio = "TRK" + System.currentTimeMillis();
        }
    }
    public enum StatusEntrega {
        SOLICITADA,
        COLETADA,
        EM_PROCESSAMENTO,
        A_CAMINHO,
        ENTREGUE,
        CANCELADA
    }

}