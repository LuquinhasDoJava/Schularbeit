package com.example.frota.produto;


import com.example.frota.caixa.Caixa;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
public class Produto {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "produto_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "caixa_id")
    private Caixa caixa;

    private BigDecimal largura;

    private BigDecimal altura;

    private BigDecimal comprimento;

    private BigDecimal peso;

    @Transient
    private BigDecimal volume;

}
