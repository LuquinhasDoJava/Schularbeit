package com.example.frota.domain.manutencao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.frota.domain.manutencao.model.Manutencao;

@Repository
@Transactional
public interface ManutencaoRepository extends JpaRepository<Manutencao, Long>{
	
}
