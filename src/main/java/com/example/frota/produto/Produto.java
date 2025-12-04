package com.example.frota.produto;

import com.example.frota.caixa.Caixa;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "produto_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caixa_id")
    private Caixa caixa;

    private BigDecimal largura;
    private BigDecimal altura;
    private BigDecimal comprimento;
    private BigDecimal peso;

    private String destino;

    @Transient
    private BigDecimal volume;

    public BigDecimal getVolume() {
        if (largura != null && altura != null && comprimento != null) {
            // Converter mm para m e calcular volume em m³
            BigDecimal volumeM3 = largura
                    .multiply(altura)
                    .multiply(comprimento)
                    .divide(BigDecimal.valueOf(1000000), 6, RoundingMode.HALF_UP); // mm³ para m³

            return volumeM3;
        }
        return BigDecimal.ZERO;
    }
}