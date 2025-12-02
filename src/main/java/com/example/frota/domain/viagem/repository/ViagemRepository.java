package com.example.frota.domain.viagem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.frota.domain.viagem.model.Viagem;

@Repository
@Transactional
public interface ViagemRepository extends JpaRepository<Viagem, Long>{
	
}
