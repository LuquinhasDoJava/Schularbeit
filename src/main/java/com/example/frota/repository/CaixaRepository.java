package com.example.frota.repository;

import com.example.frota.entity.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaixaRepository extends JpaRepository<Caixa, Long> {

    @Query("SELECT c FROM Caixa c WHERE " +
            "c.comprimento >= :comprimentoProduto AND " +
            "c.largura >= :larguraProduto AND " +
            "c.altura >= :alturaProduto AND " +
            "c.limitePeso >= :pesoProduto " +
            "ORDER BY (c.altura * c.largura * c.comprimento) ASC")
    List<Caixa> findCaixasCompativeis(
            @Param("comprimentoProduto") double comprimentoProduto,
            @Param("larguraProduto") double larguraProduto,
            @Param("alturaProduto") double alturaProduto,
            @Param("pesoProduto") double pesoProduto
    );

    // Metodo para encontrar a caixa mais próxima (primeira da lista ordenada)
    default Caixa findCaixaMaisProxima(double comprimentoProduto, double larguraProduto,
                                       double alturaProduto, double pesoProduto) {
        List<Caixa> caixasCompativeis = findCaixasCompativeis(
                comprimentoProduto, larguraProduto, alturaProduto, pesoProduto
        );
        return caixasCompativeis.isEmpty() ? null : caixasCompativeis.get(0);
    }
}