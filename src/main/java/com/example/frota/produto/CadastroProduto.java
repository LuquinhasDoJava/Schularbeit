package com.example.frota.produto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record CadastroProduto(
        @NotNull Long caixaId,
        @NotNull @Positive BigDecimal largura,
        @NotNull @Positive BigDecimal altura,
        @NotNull @Positive BigDecimal comprimento,
        @NotNull @Positive BigDecimal peso
) {
}