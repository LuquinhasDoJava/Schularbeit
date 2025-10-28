package com.example.frota.repository;

import com.example.frota.entity.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaixaRepository extends JpaRepository<Caixa, Long> {

    List<Caixa> findCaixaByAlturaLarguraComprimento(double altura, double largura, double comprimento);

}
