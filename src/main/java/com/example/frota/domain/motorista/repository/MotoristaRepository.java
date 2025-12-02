package com.example.frota.domain.motorista.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.frota.domain.motorista.model.Motorista;

@Repository
@Transactional
public interface MotoristaRepository extends JpaRepository<Motorista, Long>{
	
}
