package com.example.frota.domain.caminhao.repository;

import com.example.frota.domain.caminhao.model.Caminhao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface CaminhaoRepository extends JpaRepository<Caminhao, Long>{
	
}
