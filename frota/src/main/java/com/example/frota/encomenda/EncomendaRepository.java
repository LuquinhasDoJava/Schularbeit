package com.example.frota.encomenda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public interface EncomendaRepository extends JpaRepository<Encomenda, Integer> {

    List<Encomenda> findByStatusEntrega(Encomenda.StatusEntrega status);
    List<Encomenda> findByCaminhaoId(Long caminhaoId);
    List<Encomenda> findByCodigoRastreio(String codigoRastreio);
    List<Encomenda> findByOrigemContainingIgnoreCase(String origem);
    List<Encomenda> findByDestinoContainingIgnoreCase(String destino);
}