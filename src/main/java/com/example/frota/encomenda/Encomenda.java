package com.example.frota.encomenda;


import com.example.frota.caixa.Caixa;
import com.example.frota.caminhao.Caminhao;
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

    private double pesoCubado;

    private double peso;

    private double preco;

}
