package com.example.frota.manutencao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long> {
    List<Manutencao> findByCaminhaoIdOrderByDataManutencaoDesc(Long caminhaoId);
}