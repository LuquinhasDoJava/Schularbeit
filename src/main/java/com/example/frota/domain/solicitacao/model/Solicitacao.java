package com.example.frota.domain.solicitacao.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

import com.example.frota.domain.caixa.model.Caixa;
import com.example.frota.domain.caminhao.model.Caminhao;
import com.example.frota.domain.produto.model.Produto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "solicitacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Solicitacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "produto_id", referencedColumnName = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "caixa_id", referencedColumnName = "caixa_id")
    private Caixa caixa;
    
    @ManyToOne
    @JoinColumn(name = "caminhao_id", referencedColumnName = "caminhao_id")
    private Caminhao caminhao;

    private String cepOrigem;
    private String cepDestino;
    private LocalDateTime dataSolicitacao;

    private Double distanciaKm;
    private Double custoPedagios;
    private Double pesoConsideradoKg;
    private Double custoFreteCalculado;
    
}