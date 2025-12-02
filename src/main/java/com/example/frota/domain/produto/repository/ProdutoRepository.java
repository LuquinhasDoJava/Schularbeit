package com.example.frota.domain.produto.repository;

import com.example.frota.domain.produto.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("""
            SELECT p
            FROM Produto p
            LEFT JOIN Solicitacao s ON s.produto = p
            WHERE s.id IS NULL
            """)
    List<Produto> procurarProdutosSemSolicitacao();
}
