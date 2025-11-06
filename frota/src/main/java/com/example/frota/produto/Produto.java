package com.example.frota.produto;


import com.example.frota.caixa.Caixa;
import jakarta.persistence.*;
import lombok.*;


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

    private double largura;

    private double altura;

    private double comprimento;

    private double peso;

    @Transient
    private double volume;

}
