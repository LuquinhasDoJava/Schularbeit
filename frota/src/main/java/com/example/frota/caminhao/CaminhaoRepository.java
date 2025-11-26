package com.example.frota.caminhao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CaminhaoRepository extends JpaRepository<Caminhao, Long> {
    List<Caminhao> findByStatusManutencao(Caminhao.StatusManutencao status);
    List<Caminhao> findByOrderByVolumeDesc();
}